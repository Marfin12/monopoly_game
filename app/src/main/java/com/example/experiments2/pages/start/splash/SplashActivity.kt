package com.example.experiments2.pages.start.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.experiments2.R
import com.example.experiments2.component.dialog.GameMessage
import com.example.experiments2.constant.Constant
import com.example.experiments2.pages.menu.MenuActivity
import com.example.experiments2.pages.start.StartActivity
import com.example.experiments2.util.Util.handleErrorMessage
import com.example.experiments2.viewmodel.ViewModelEnum


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runSplashScreen()
    }

    private fun runSplashScreen() {
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        splashViewModel.vmData.observe(this) { vmData ->
            if (vmData != null) {
                when (vmData.status) {
                    ViewModelEnum.SUCCESS -> {
                        if (vmData.data?.isSuccessful == true) {
                            MenuActivity.launch(this@SplashActivity)
                            finish()
                        } else {
                            StartActivity.launch(this@SplashActivity)
                            finish()
                        }
                    }
                    else -> {}
                }

                handleErrorMessage(this, vmData.status, vmData.errorMessage) {
                    this.finish()
                }
            }
        }

        splashViewModel.loadUserData(this@SplashActivity)
    }
}