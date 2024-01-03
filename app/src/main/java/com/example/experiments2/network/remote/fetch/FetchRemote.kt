package com.example.experiments2.network.remote.fetch

class FetchRemote(
    var onLoading: (() -> Unit)? = null,
    var onDataRetrieved: ((Any?) -> Unit)? = null,
    var onCancelled: ((String?) -> Unit)? = null,
    var onComplete: (() -> Unit)? = null
) {
    companion object {
        @Volatile
        private var instance: FetchRemote? = null

        fun getInstance(): FetchRemote =
            instance ?: synchronized(this) { instance ?: FetchRemote() }
    }
}