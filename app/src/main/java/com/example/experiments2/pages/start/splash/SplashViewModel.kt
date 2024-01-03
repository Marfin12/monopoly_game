package com.example.experiments2.pages.start.splash

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.example.experiments2.network.FirebaseRepository
import com.example.experiments2.network.FirebaseUtil.firebaseObserver
import com.example.experiments2.pages.start.StartData
import com.example.experiments2.viewmodel.ViewModelBase
import com.example.experiments2.viewmodel.ViewModelData
import com.example.experiments2.viewmodel.ViewModelEnum


class SplashViewModel : ViewModelBase<StartData, FirebaseRepository>(FirebaseRepository.getInstance()) {
    fun loadUserData(context: Context) {
        repository.loadUserData(context,
            firebaseObserver(
                onDataRetrieved = { isUserExist ->
                    _vmData.value = ViewModelData(
                        ViewModelEnum.SUCCESS,
                        StartData(isUserExist == true)
                    )
                },
                onCancelled = { error -> this.onError(error) },
                onLoading = { this.onLoading() },
                onComplete = { this.onComplete(null) }
            )
        )
    }
}