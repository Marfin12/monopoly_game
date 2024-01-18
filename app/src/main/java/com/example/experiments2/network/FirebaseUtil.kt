package com.example.experiments2.network

import android.content.Context
import androidx.annotation.Keep
import com.example.experiments2.MyApplication
import com.example.experiments2.MyApplication.Companion.firebaseRemote
import com.example.experiments2.constant.Constant
import com.example.experiments2.constant.Constant.ErrorType.OPERATION_LOCAL_FAILED
import com.example.experiments2.network.remote.fetch.FetchRemote
import com.example.experiments2.network.remote.fetch.FirebaseRemote
import com.example.experiments2.network.remote.fetch.GameApi
import com.example.experiments2.network.remote.response.user.match.MatchData
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import com.google.firebase.database.DataSnapshot


@Keep
object FirebaseUtil {

    fun checkToken(context: Context, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        val user = MyApplication.gamePreference.loadPreference<ProfileData?>(
            context, GameApi.UserProfile.Field.USER_EMAIL
        )

        if (user != null) {
            FirebaseRemote.getInstance().getFirebaseData(
                GameApi.UserProfile.accessProfileApi(
                    user.useremail,
                    GameApi.UserProfile.Field.USER_TOKEN
                ),
                firebaseObserver(
                    onDataRetrieved = { dataSnapshot ->
                        val snapshot = dataSnapshot as DataSnapshot?
                        if (snapshot != null) {
                            val token = snapshot.value as String?

                            if (token == user.usertoken)
                                fetchRemote.onDataRetrieved?.invoke(true)
                            else {
                                fetchRemote.onDataRetrieved?.invoke(false)
                            }
                        } else {
                            fetchRemote.onDataRetrieved?.invoke(false)
                        }
                    },
                    onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                    onComplete = { fetchRemote.onComplete?.invoke() }
                )
            )
        } else {
            fetchRemote.onDataRetrieved?.invoke(false)
        }
    }

    fun firebaseObserver(
        onLoading: (() -> Unit)? = null,
        onDataRetrieved: ((Any?) -> Unit)? = null,
        onCancelled: ((String?) -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ): FetchRemote {
        val firebaseRemote = FetchRemote.getInstance()

        firebaseRemote.onLoading = onLoading
        firebaseRemote.onDataRetrieved = onDataRetrieved
        firebaseRemote.onCancelled = onCancelled
        firebaseRemote.onComplete = onComplete

        return firebaseRemote
    }

    fun validatePath(apiPath: MutableList<String>): MutableList<String> =
        apiPath.map { path ->
            path.replace(Regex("[^A-Za-z0-9_]"), "")
        }.toMutableList()

    inline fun <reified T> getDataFromAnyList(anyVar: MutableList<*>): MutableList<T> {
        val matchData = mutableListOf<T>()

        anyVar.map { data ->
            if (data is T) {
                matchData.add(data)
            }
        }

        return matchData
    }
}
