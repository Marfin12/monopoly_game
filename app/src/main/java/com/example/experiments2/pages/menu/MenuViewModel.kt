package com.example.experiments2.pages.menu

import android.content.Context
import android.net.Uri
import com.example.experiments2.network.FirebaseUtil
import com.example.experiments2.network.remote.response.user.UserData
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import com.example.experiments2.network.repository.MenuRepository
import com.example.experiments2.viewmodel.ViewModelBase
import com.example.experiments2.viewmodel.ViewModelData
import com.example.experiments2.viewmodel.ViewModelEnum


class MenuViewModel : ViewModelBase<MenuData, MenuRepository>(MenuRepository.getInstance()) {
    fun saveSetting(context: Context, menuData: MenuData) {
        repository.saveSetting(context, menuData, FirebaseUtil.firebaseObserver(
            onDataRetrieved = { _ ->
                _vmData.value = ViewModelData(
                    ViewModelEnum.SUCCESS,
                    MenuData(state = MenuEnum.CLOSE_SETTING)
                )
            },
            onCancelled = { error -> this.onError(error) },
            onLoading = { this.onLoading() },
            onComplete = { this.onComplete(null) }
        ))
    }

    fun loadSetting(context: Context) {
        repository.loadSetting(context, FirebaseUtil.firebaseObserver(
            onDataRetrieved = { anyData ->
                val data = anyData as MenuData?

                data?.state = MenuEnum.OPEN_SETTING
                _vmData.value = ViewModelData(ViewModelEnum.SUCCESS, data)
            },
            onCancelled = { error -> this.onError(error) },
            onLoading = { this.onLoading() },
            onComplete = { this.onComplete(null) }
        ))
    }

    fun loadProfile(context: Context, isInit: Boolean) {
        val menuState = if (isInit) MenuEnum.ONBOARDING else MenuEnum.OPEN_PROFILE

        repository.loadProfile(context, FirebaseUtil.firebaseObserver(
            onDataRetrieved = { userData ->
                _vmData.value = ViewModelData(
                    ViewModelEnum.SUCCESS,
                    MenuData(userData as UserData, state = menuState)
                )
            },
            onCancelled = { error -> this.onError(error) },
            onLoading = { this.onLoading() },
            onComplete = { this.onComplete(null) }
        ))
    }

    fun updateProfileField(context: Context, field: String, value: String) {
        repository.updateProfileField(context, field, value, FirebaseUtil.firebaseObserver(
            onDataRetrieved = { _ ->
                _vmData.value = ViewModelData(
                    ViewModelEnum.SUCCESS,
                    MenuData(state = MenuEnum.UPDATE_PROFILE)
                )
            },
            onCancelled = { error -> this.onError(error) },
            onLoading = {
                _vmData.value = ViewModelData(
                    ViewModelEnum.LOADING,
                    MenuData(state = MenuEnum.UPDATE_PROFILE)
                )
            },
            onComplete = { this.onComplete(null) }
        ))
    }

    fun updatePhotoProfile(context: Context, uri: Uri) {
        repository.uploadPhotoProfile(context, uri, FirebaseUtil.firebaseObserver(
            onDataRetrieved = { uploadUri ->
                _vmData.value = ViewModelData(
                    ViewModelEnum.SUCCESS,
                    MenuData(state = MenuEnum.UPDATE_PROFILE, uri = uploadUri as Uri?)
                )
            },
            onCancelled = { error -> this.onError(error) },
            onLoading = {
                _vmData.value = ViewModelData(
                    ViewModelEnum.LOADING,
                    data = MenuData(state = MenuEnum.UPDATE_PROFILE)
                )
                println(_vmData.value)
            },
            onComplete = { this.onComplete(null) }
        ))
    }
}