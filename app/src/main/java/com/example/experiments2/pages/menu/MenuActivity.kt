package com.example.experiments2.pages.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AppOpsManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.experiments2.MyApplication
import com.example.experiments2.R
import com.example.experiments2.component.dialog.GameMessage
import com.example.experiments2.component.dialog.profile.GameProfile
import com.example.experiments2.component.dialog.room.GameRoomCard
import com.example.experiments2.component.dialog.GameSettings
import com.example.experiments2.databinding.ActivityMenuBinding
import com.example.experiments2.network.remote.fetch.GameApi
import com.example.experiments2.network.remote.response.settings.SettingData
import com.example.experiments2.network.remote.response.user.UserData
import com.example.experiments2.pages.ActivityBase
import com.example.experiments2.pages.main.MainActivity
import com.example.experiments2.pages.menu.fragments.CreateJoinService
import com.example.experiments2.pages.menu.fragments.TabAdapter
import com.example.experiments2.pages.start.StartActivity
import com.example.experiments2.util.Util.handleErrorMessage
import com.example.experiments2.util.Util.toDisable
import com.example.experiments2.util.Util.toEnable
import com.example.experiments2.viewmodel.ViewModelEnum


class MenuActivity : ActivityBase<ActivityMenuBinding>() {

    private lateinit var menuViewModel: MenuViewModel

    private lateinit var gameSettings: GameSettings

    private var gameRoomCard: GameRoomCard? = null
    private var gameProfile: GameProfile? = null

    private lateinit var errorMessage: GameMessage

    private var userData: UserData? = null


    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MenuActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityMenuBinding {
        return ActivityMenuBinding.inflate(layoutInflater)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initComponent()
//        checkUsageStatsPermission()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    private fun initComponent() {
        gameSettings = GameSettings.newInstance(this)
        gameProfile = GameProfile.newInstance(this)
        errorMessage = GameMessage.newInstance(this)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = TabAdapter(
            supportFragmentManager,
            roomCard = { roomData, leaveTheRoom ->
                gameRoomCard = GameRoomCard.newInstance(this)

                gameRoomCard?.onDismissListener = {
                    onDismissDialog()
                    leaveTheRoom.invoke()
                }

                gameRoomCard?.show(roomData)
            },
            onShowLoading = {
                onShowLoading()
            },
            onHideLoading = {
                onHideLoading()
            },
            onForceLogout = {
                forceLogout()
            },
            updateRoomCard = { roomData ->
                gameRoomCard?.generateMessage(roomData)
            }
        )

        binding.ivMore.setOnClickListener {
            menuViewModel.loadSetting(this)
        }

        binding.ivProfile.setOnClickListener {
            if (userData != null) {
                showProfileDialog()
            } else {
                menuViewModel.loadProfile(this, false)
            }
        }
    }

    private fun initViewModel() {
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        initObserve()
        menuViewModel.loadProfile(this, true)
    }

    private fun initObserve() {
        menuViewModel.vmData.observe(this) { vmData ->
            if (vmData != null) {
                val menuData = vmData.data

                when (vmData.status) {
                    ViewModelEnum.LOADING -> {
                        if (menuData?.state == MenuEnum.UPDATE_PROFILE)
                            gameProfile?.showLoading()
                        else onShowLoading()
                    }

                    ViewModelEnum.SUCCESS -> {
                        if (menuData != null) {
                            when (menuData.state) {
                                MenuEnum.OPEN_SETTING -> {
                                    onShowDialog()

                                    gameSettings.apply {
                                        onDismissListener = { onDismissDialog() }
                                        show(
                                            menuData.settingData.sound,
                                            menuData.settingData.music,
                                            getString(R.string.str_ok),
                                            getString(R.string.menu_setting_reset),
                                            onPositiveButtonClick = { sound, music ->
                                                menuViewModel.saveSetting(
                                                    this@MenuActivity,
                                                    MenuData(
                                                        settingData = SettingData(
                                                            sound,
                                                            music
                                                        )
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }

                                MenuEnum.CLOSE_SETTING -> {
                                    gameSettings.dismiss()
                                }

                                MenuEnum.OPEN_PROFILE -> {
                                    userData = vmData.data.userData
                                    showProfileDialog()
                                }

                                MenuEnum.ONBOARDING -> {
                                    userData = vmData.data.userData
                                }

                                MenuEnum.UPDATE_PROFILE -> {
                                    val uploadedUri = menuData.uri

                                    if (uploadedUri != null) {
                                        userData?.profileData?.userimage = uploadedUri.toString()
                                        gameProfile?.previewImage(uploadedUri)
                                    } else {
                                        userData?.profileData?.username = menuData.updatedUserName
                                        gameProfile?.onUpdateUsernameSuccess()
                                    }
                                }

                                else -> {}
                            }
                        } else {
                            forceLogout()
                        }
                    }

                    ViewModelEnum.COMPLETE -> {
                        onHideLoading()
                    }

                    else -> {}
                }

                handleErrorMessage(errorMessage, vmData.status, vmData.errorMessage) {
                    gameProfile?.hideLoading()
                    onHideLoading()
                }
            }
        }
    }

    private fun checkUsageStatsPermission() {
        val appOps = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)

        if (mode != AppOpsManager.MODE_ALLOWED) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
    }

    private fun showProfileDialog() {
        onShowDialog()

        gameProfile = GameProfile.newInstance(this)
        gameProfile?.apply {
            onDismissListener = { onDismissDialog() }
            onChangePhoto = { startGallery() }
            onDoneEditUsername = { username ->
                menuViewModel.updateProfileField(
                    this@MenuActivity,
                    GameApi.UserProfile.Field.USER_NAME,
                    username
                )
            }

            show(userData)
        }
    }

    private fun onShowLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.ivProfile.toDisable()
        binding.ivMore.toDisable()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onHideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.ivProfile.toEnable()
        binding.ivMore.toEnable()
    }

    private fun onShowDialog() {
        binding.tabLayout.isEnabled = false
    }

    private fun onDismissDialog() {
        binding.tabLayout.isEnabled = true
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun forceLogout() {
        handleErrorMessage(
            errorMessage,
            ViewModelEnum.ERROR,
            getString(R.string.error_force_logout_content),
            getString(R.string.error_force_logout_title)
        ) {
            StartActivity.launch(this@MenuActivity)
            finish()
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                menuViewModel.updatePhotoProfile(this@MenuActivity, uri)
            }
        }
    }

}
