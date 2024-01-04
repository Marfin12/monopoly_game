package com.example.experiments2.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class ViewModelBase<T : Parcelable, K : Any?>(var repository: K) : ViewModel() {
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
