package com.example.experiments2.pages.start

import android.app.Activity
import android.content.Context
import com.example.experiments2.network.repository.StartRepository
import com.example.experiments2.network.FirebaseUtil.firebaseObserver
import com.example.experiments2.viewmodel.ViewModelBase
import com.example.experiments2.viewmodel.ViewModelData
import com.example.experiments2.viewmodel.ViewModelEnum


class StartViewModel : ViewModelBase<StartData, StartRepository>(StartRepository.getInstance()) {
    fun loginWithGoogle(activity: Activity, idToken: String) {
        repository.loginWithGoogle(activity, idToken, firebaseObserver(
            onDataRetrieved = { error -> onLoginSuccess(error) },
            onCancelled = { error -> this.onError(error) },
            onLoading = { this.onLoading() },
            onComplete = { this.onComplete(null) }
        ))
    }

    fun loginWithGuest(context: Context) {
        repository.loginWithGuest(context, firebaseObserver(
            onDataRetrieved = { error -> onLoginSuccess(error) },
            onCancelled = { error -> this.onError(error) },
            onLoading = { this.onLoading() },
            onComplete = { this.onComplete(null) }
        ))
    }

    private fun onLoginSuccess(error: Any?) {
        if (error is String) {
            _vmData.value = ViewModelData(ViewModelEnum.ERROR, null, error)
        }
        else {
            _vmData.value = ViewModelData(
                ViewModelEnum.SUCCESS,
                StartData(true)
            )
        }
    }
}