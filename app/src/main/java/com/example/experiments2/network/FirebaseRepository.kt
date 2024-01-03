package com.example.experiments2.network

import android.app.Activity
import android.content.Context
import com.example.experiments2.MyApplication.Companion.firebaseRemote
import com.example.experiments2.MyApplication.Companion.gamePreference
import com.example.experiments2.constant.Constant.ErrorType.OPERATION_LOCAL_FAILED
import com.example.experiments2.network.FirebaseUtil.firebaseObserver
import com.example.experiments2.network.remote.fetch.FetchRemote
import com.example.experiments2.network.remote.fetch.FirebaseRemote
import com.example.experiments2.network.remote.fetch.GameApi
import com.example.experiments2.network.remote.fetch.GameApi.UserProfile.Field.USER_STATE
import com.example.experiments2.network.remote.fetch.GameApi.UserProfile.Field.USER_TOKEN
import com.example.experiments2.network.remote.fetch.GameApi.UserProfile.accessProfileApi
import com.example.experiments2.network.remote.response.user.ProfileData
import com.example.experiments2.network.remote.response.user.ProfileEnum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FirebaseRepository {
    fun loadUserData(context: Context, fetchRemote: FetchRemote) {
        val user = gamePreference.loadPreference<ProfileData?>(
            context, GameApi.UserProfile.Field.USER_NAME
        )

        if (user != null) {
            FirebaseRemote.getInstance().getFirebaseData(
                accessProfileApi(user.username, USER_TOKEN),
                firebaseObserver(
                    onDataRetrieved = { dataSnapshot ->
                        val snapshot = dataSnapshot as DataSnapshot?
                        if (snapshot != null) {
                            val token = snapshot.value as String?

                            if (token == user.token)
                                fetchRemote.onDataRetrieved?.invoke(true)
                            else {
                                firebaseRemote.logoutGoogle(fetchRemote)
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

    fun loginWithGuest(context: Context, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        val guestData = firebaseRemote.guestData(context)

        checkUserInDatabase(
            guestData.username,
            firebaseObserver(
                onDataRetrieved = { _ -> // ignore non existed data, just replace it for guest mode
                    registerNewUser(
                        context, guestData.username, guestData.token, fetchRemote
                    )
                },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() }
            )
        )
    }

    fun loginWithGoogle(activity: Activity, idToken: String, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        val authCredential = GoogleAuthProvider.getCredential(idToken, null)
        val auth = FirebaseAuth.getInstance()

        auth.signInWithCredential(authCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val email = user?.email

                if (!email.isNullOrBlank()) {
                    checkUserInDatabase(
                        email,
                        firebaseObserver(
                            onDataRetrieved = { dataSnapshot ->
                                firebaseRemote.getTokenFromEmail(
                                    user,
                                    onSuccessful = { token ->
                                        val snapshot = dataSnapshot as DataSnapshot

                                        if (snapshot.exists()) {
                                            updateToken(activity, email, token, fetchRemote)
                                        } else {
                                            registerNewUser(
                                                activity, email, token, fetchRemote
                                            )
                                        }
                                    },
                                    onFailure = { error ->
                                        fetchRemote.onCancelled?.invoke(error)
                                    }
                                )
                            },
                            onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                            onComplete = { fetchRemote.onComplete?.invoke() }
                        )
                    )
                }
            }
        }.addOnFailureListener {
            fetchRemote.onCancelled?.invoke(it.message)
            fetchRemote.onComplete?.invoke()
        }.addOnCanceledListener {
            fetchRemote.onCancelled?.invoke(OPERATION_LOCAL_FAILED)
            fetchRemote.onComplete?.invoke()
        }
    }

    private fun checkUserInDatabase(username: String, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        FirebaseRemote.getInstance().getFirebaseData(
            accessProfileApi(username),
            firebaseObserver(
                onDataRetrieved = { snapshot ->
                    fetchRemote.onDataRetrieved?.invoke(snapshot)
                },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() }
            )
        )
    }

    private fun registerNewUser(
        context: Context,
        username: String,
        userToken: String,
        fetchRemote: FetchRemote
    ) {
        val playerFields = HashMap<String, Any>()

        playerFields[GameApi.UserProfile.Field.USER_NAME] = username
        playerFields[GameApi.UserProfile.Field.USER_EMAIL] = username
        playerFields[GameApi.UserProfile.Field.USER_IMAGE] = ""
        playerFields[GameApi.UserProfile.Field.USER_DATE] =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        playerFields[GameApi.UserProfile.Field.USER_GAMES] = 0
        playerFields[GameApi.UserProfile.Field.USER_RATING] = 0
        playerFields[USER_STATE] = ProfileEnum.SEARCHING_ROOM
        playerFields[USER_TOKEN] = userToken

        firebaseRemote.insertFirebaseData(
            playerFields,
            accessProfileApi(username),
            firebaseObserver(
                onDataRetrieved = { error ->
                    try {
                        gamePreference.savePreference(
                            context,
                            GameApi.UserProfile.Field.USER_NAME,
                            ProfileData(username, userToken)
                        )

                        fetchRemote.onDataRetrieved?.invoke(error)
                    } catch (ex: Exception) {
                        fetchRemote.onCancelled?.invoke(ex.message)
                    }
                },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() }
            )
        )
    }

    private fun updateToken(
        context: Context,
        username: String,
        userToken: String,
        fetchRemote: FetchRemote
    ) {
        val playerFields = HashMap<String, Any>()

        playerFields[USER_TOKEN] = userToken

        gamePreference.savePreference(
            context,
            GameApi.UserProfile.Field.USER_NAME,
            ProfileData(username, userToken)
        )

        firebaseRemote.insertFirebaseData(
            playerFields,
            accessProfileApi(username),
            firebaseObserver(
                onDataRetrieved = { error -> fetchRemote.onDataRetrieved?.invoke(error) },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() }
            )
        )
    }

    companion object {
        @Volatile
        private var instance: FirebaseRepository? = null
        fun getInstance(): FirebaseRepository =
            instance ?: synchronized(this) {
                instance ?: FirebaseRepository()
            }.also { instance = it }
    }
}