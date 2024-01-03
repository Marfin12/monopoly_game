package com.example.experiments2.network.local

import android.content.Context
import com.example.experiments2.constant.Constant
import com.google.gson.Gson
import java.lang.Exception

class GamePreference {
    fun savePreference(context: Context, key: String, value: Any) {
        try {
            val sharedPreferences = context.getSharedPreferences(
                Constant.LOCAL_STORAGE_NAME,
                Context.MODE_PRIVATE
            )

            val editor = sharedPreferences.edit()

            when (value) {
                is String -> editor.putString(key, value)
                is Int -> editor.putInt(key, value)
                is Float -> editor.putFloat(key, value)
                is Boolean -> editor.putBoolean(key, value)
                else -> {
                    editor.putString(key, Gson().toJson(value))
                }
            }

            editor.apply()
        } catch(ex: Exception) {
            println(ex.message)
        }
    }

    fun clearPreference(context: Context) {
        val sharedPreferences = context.getSharedPreferences(
            Constant.LOCAL_STORAGE_NAME,
            Context.MODE_PRIVATE
        )

        sharedPreferences.edit().clear().apply()
    }

    inline fun <reified T> loadPreference(context: Context, key: String): T? {
        val sharedPreferences = context.getSharedPreferences(
            Constant.LOCAL_STORAGE_NAME,
            Context.MODE_PRIVATE
        )

        val value: Any? = when (T::class.java) {
            String::class.java -> sharedPreferences.getString(key, "") ?: ""
            Int::class.java -> sharedPreferences.getInt(key, -1)
            Float::class.java -> sharedPreferences.getFloat(key, -1F)
            Boolean::class.java -> sharedPreferences.getBoolean(key, false)
            else -> Gson().fromJson(sharedPreferences.getString(key, null), T::class.java)
        }

        return value as T
    }

    companion object {
        @Volatile
        private var instance: GamePreference? = null

        fun getInstance(): GamePreference =
            instance ?: synchronized(this) { instance ?: GamePreference() }
    }
}