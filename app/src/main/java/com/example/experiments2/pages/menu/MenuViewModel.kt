package com.example.experiments2.pages.menu

import android.content.Context
import com.example.experiments2.network.FirebaseUtil
import com.example.experiments2.network.repository.MenuRepository
import com.example.experiments2.network.repository.StartRepository
import com.example.experiments2.pages.start.StartData
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
}