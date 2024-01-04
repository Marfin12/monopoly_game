package com.example.experiments2.pages.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.experiments2.R
import com.example.experiments2.component.dialog.GameProfile
import com.example.experiments2.component.dialog.GameRoomCard
import com.example.experiments2.component.dialog.GameSettings
import com.example.experiments2.databinding.ActivityMenuBinding
import com.example.experiments2.pages.ActivityBase
import com.example.experiments2.pages.main.MainActivity
import com.example.experiments2.pages.menu.fragments.TabAdapter
import com.example.experiments2.pages.start.StartActivity
import com.example.experiments2.pages.start.splash.SplashViewModel
import com.example.experiments2.util.Util
import com.example.experiments2.viewmodel.ViewModelEnum


class MenuActivity : ActivityBase<ActivityMenuBinding>() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var gameSettings: GameSettings

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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    private fun initComponent() {
        gameSettings = GameSettings.newInstance(this)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = TabAdapter(supportFragmentManager) { title ->
            onShowDialog()
            GameRoomCard.newInstance(this).apply {
                onDismissListener = { onDismissDialog() }
                show(title, onPositiveButtonClick = {
                    MainActivity.launch(this@MenuActivity)
                })
            }
        }

        binding.ivMore.setOnClickListener {
            menuViewModel.loadSetting(this)
        }

        binding.ivProfile.setOnClickListener {
            onShowDialog()
            GameProfile.newInstance(this).apply {
                onDismissListener = { onDismissDialog() }
                show()
            }
        }
    }

    private fun initViewModel() {
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        menuViewModel.vmData.observe(this) { vmData ->
            if (vmData != null) {
                when (vmData.status) {
                    ViewModelEnum.LOADING -> onShowLoading()
                    ViewModelEnum.SUCCESS -> {
                        val menuData = vmData.data
                        if (menuData?.state == MenuEnum.OPEN_SETTING) {
                            onShowDialog()

                            gameSettings.apply {
                                onDismissListener = { onDismissDialog() }
                                show(
                                    menuData.sound,
                                    menuData.music,
                                    getString(R.string.str_ok),
                                    getString(R.string.menu_setting_reset),
                                    onPositiveButtonClick = { sound, music ->
                                        menuViewModel.saveSetting(
                                            this@MenuActivity,
                                            MenuData(sound, music)
                                        )
                                    }
                                )
                            }
                        } else if (menuData?.state == MenuEnum.CLOSE_SETTING) {
                            gameSettings.dismiss()
                        }
                    }
                    ViewModelEnum.ERROR -> {
                        showToastShort(getString(R.string.error_title))
                    }
                    ViewModelEnum.COMPLETE -> onHideLoading()
                }

                Util.handleErrorMessage(this, vmData.status, vmData.errorMessage) {
                    this.finish()
                }
            }
        }
    }


    private fun onShowLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onHideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun onShowDialog() {
        binding.tabLayout.isEnabled = false
    }

    private fun onDismissDialog() {
        binding.tabLayout.isEnabled = true
    }
}