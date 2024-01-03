package com.example.experiments2.network

import androidx.annotation.Keep
import com.example.experiments2.network.remote.fetch.FetchRemote


@Keep
object FirebaseUtil {
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

    fun validatePath(apiPath: MutableList<String>) : MutableList<String> =
        apiPath.map { path ->
            path.replace(Regex("[^A-Za-z0-9_]"), "")
        }.toMutableList()
}
