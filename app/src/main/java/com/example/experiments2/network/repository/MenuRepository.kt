package com.example.experiments2.network.repository

import android.content.Context
import com.example.experiments2.MyApplication.Companion.gamePreference
import com.example.experiments2.network.remote.fetch.FetchRemote
import com.example.experiments2.network.remote.fetch.GameApi
import com.example.experiments2.pages.menu.MenuData

class MenuRepository {

    fun saveSetting(context: Context, menuData: MenuData, fetchRemote: FetchRemote) {
        try {
            gamePreference.savePreference(context, GameApi.SETTINGS, menuData)

            fetchRemote.onDataRetrieved?.invoke(null)
        } catch (ex: Exception) {
            fetchRemote.onCancelled?.invoke(ex.message)
        }
        finally {
            fetchRemote.onComplete?.invoke()
        }
    }

    fun loadSetting(context: Context, fetchRemote: FetchRemote) {
        try {
            val menuData =
                gamePreference.loadPreference<MenuData?>(context, GameApi.SETTINGS) ?: MenuData()

            fetchRemote.onDataRetrieved?.invoke(menuData)
        } catch (ex: Exception) {
            fetchRemote.onCancelled?.invoke(ex.message)
        }
        finally {
            fetchRemote.onComplete?.invoke()
        }
    }

    companion object {
        @Volatile
        private var instance: MenuRepository? = null
        fun getInstance(): MenuRepository =
            instance ?: synchronized(this) {
                instance ?: MenuRepository()
            }.also { instance = it }
    }
}