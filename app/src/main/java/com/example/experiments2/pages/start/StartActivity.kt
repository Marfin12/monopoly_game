package com.example.experiments2.pages.start

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.experiments2.R
import com.example.experiments2.component.dialog.GameMessage
import com.example.experiments2.constant.Constant.ErrorType.CANCELLED_USER_ERROR_CODE
import com.example.experiments2.constant.Constant.RC_SIGN_IN
import com.example.experiments2.databinding.ActivityStartBinding
import com.example.experiments2.pages.ActivityBase
import com.example.experiments2.pages.menu.MenuActivity
import com.example.experiments2.util.Util.handleErrorMessage
import com.example.experiments2.util.Util.playGif
import com.example.experiments2.viewmodel.ViewModelEnum
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class StartActivity : ActivityBase<ActivityStartBinding>() {

    private lateinit var startViewModel: StartViewModel

    private lateinit var mGoogleSignInClient: GoogleSignInClient


    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StartActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playGif(this@StartActivity, R.drawable.splash_monopoly_deal, binding.ivMonopolyIcon)

        initGoogle()
        initViewModel()
        initComponent()
    }

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityStartBinding {
        return ActivityStartBinding.inflate(layoutInflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        onHideLoading()

        try {
            super.onActivityResult(requestCode, resultCode, data)
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            if (!account.idToken.isNullOrBlank()) {
                startViewModel.loginWithGoogle(this@StartActivity, account.idToken ?: "")
            } else {
                showToastShort(resources.getString(R.string.error_network_connection_title))
            }
        } catch(ex: Exception) {
            val errMessage = ex.message?.replace(" ", "")

            if (errMessage == CANCELLED_USER_ERROR_CODE) {
                showToastLong(resources.getString(R.string.error_google_cancelled))
            } else {
                showToastShort(resources.getString(R.string.error_network_connection_title))
            }
        }
    }

    private fun initGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun initComponent() {
        binding.btnConnectGoogle.setOnClickListener {
            onShowLoading()

            mGoogleSignInClient.revokeAccess().addOnCompleteListener {
                val intent = mGoogleSignInClient.signInIntent
                startActivityForResult(intent, RC_SIGN_IN)
            }
        }

        binding.btnJoinGuest.setOnClickListener {
            GameMessage.newInstance(this).show(
                resources.getString(R.string.start_message_guest_title),
                resources.getString(R.string.start_message_guest_content),
                resources.getString(R.string.start_message_guest_positive),
                resources.getString(R.string.start_message_guest_negative),
                onPositiveButtonClick = {
                    startViewModel.loginWithGuest(this@StartActivity)
                }
            )
        }
    }

    private fun initViewModel() {
        startViewModel = ViewModelProvider(this)[StartViewModel::class.java]

        startViewModel.vmData.observe(this) { vmData ->
            if (vmData != null) {
                when (vmData.status) {
                    ViewModelEnum.LOADING -> onShowLoading()
                    ViewModelEnum.SUCCESS -> {
                        if (vmData.data?.isSuccessful == true) {
                            MenuActivity.launch(this@StartActivity)
                            finish()
                        }
                    }
                    ViewModelEnum.COMPLETE -> onHideLoading()
                    else -> {}
                }

                handleErrorMessage(this, vmData.status, vmData.errorMessage)
            }
        }
    }

    private fun onShowLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnJoinGuest.isEnabled = false
        binding.btnConnectGoogle.isEnabled = false
    }

    private fun onHideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnJoinGuest.isEnabled = true
        binding.btnConnectGoogle.isEnabled = true
    }
}
