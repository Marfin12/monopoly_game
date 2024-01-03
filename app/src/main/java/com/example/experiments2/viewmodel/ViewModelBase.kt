package com.example.experiments2.viewmodel

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.experiments2.MyApplication.Companion.appResources
import com.example.experiments2.R
import com.example.experiments2.component.dialog.GameMessage
import com.example.experiments2.constant.Constant
import com.example.experiments2.network.FirebaseRepository


open class ViewModelBase<T : Parcelable, K : FirebaseRepository?>(var repository: K) : ViewModel() {
    val _vmData: MutableLiveData<ViewModelData<T>?> =
        MutableLiveData<ViewModelData<T>?>()
    val vmData: LiveData<ViewModelData<T>?>
        get() = _vmData


    fun onComplete(data: T?) {
        _vmData.value = ViewModelData(ViewModelEnum.COMPLETE, data)
    }

    fun onError(error: String?) {
        _vmData.value = ViewModelData(ViewModelEnum.ERROR, null, error)
    }

    fun onLoading() {
        _vmData.value = ViewModelData(ViewModelEnum.LOADING, null)
    }
}
