package com.example.experiments2.network.repository

import android.app.Activity
import android.content.Context
import com.example.experiments2.MyApplication.Companion.firebaseRemote
import com.example.experiments2.MyApplication.Companion.gamePreference
import com.example.experiments2.constant.Constant.ErrorType.OPERATION_LOCAL_FAILED
import com.example.experiments2.network.FirebaseUtil.allowedFirebaseString
import com.example.experiments2.network.FirebaseUtil.checkToken
import com.example.experiments2.network.FirebaseUtil.firebaseObserver
import com.example.experiments2.network.remote.fetch.FetchRemote
import com.example.experiments2.network.remote.fetch.FirebaseRemote
import com.example.experiments2.network.remote.fetch.GameApi
import com.example.experiments2.network.remote.fetch.GameApi.UserProfile.Field.USER_STATE
import com.example.experiments2.network.remote.fetch.GameApi.UserProfile.Field.USER_TOKEN
import com.example.experiments2.network.remote.fetch.GameApi.UserProfile.accessProfileApi
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import com.example.experiments2.network.remote.response.user.profile.ProfileEnum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StartRepository {
    fun loadUserData(context: Context, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        checkToken(context, firebaseObserver(
            onDataRetrieved = { isValidToken ->
                if (isValidToken == true)
                    fetchRemote.onDataRetrieved?.invoke(true)
                else {
                    firebaseRemote.logoutGoogle(fetchRemote)
                }
            },
            onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
            onComplete = { fetchRemote.onComplete?.invoke() }
        ))
    }

    fun loginWithGuest(context: Context, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        val guestData = firebaseRemote.guestData(context)

        checkUserInDatabase(
            guestData.useremail,
            firebaseObserver(
                onDataRetrieved = { _ -> // ignore non existed data, just replace it for guest mode
                    val email = guestData.useremail
                    val profileData = ProfileData(
                        allowedFirebaseString(email), email,
                        SimpleDateFormat(" dd MMM yyyy", Locale.getDefault()).format(Date()),
                        0, "", 0, "",
                        ProfileEnum.SEARCHING_ROOM,
                        guestData.usertoken
                    )

                    registerNewUser(context, profileData, fetchRemote)
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
                                        val profileData = ProfileData(
                                            allowedFirebaseString(email), email,
                                            SimpleDateFormat(" dd MMM yyyy", Locale.getDefault()).format(Date()),
                                            0, "", 0, "",
                                            ProfileEnum.SEARCHING_ROOM,
                                            token
                                        )

                                        if (snapshot.exists()) {
                                            updateToken(activity, profileData, fetchRemote)
                                        } else {
                                            registerNewUser(activity, profileData, fetchRemote)
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

    private fun checkUserInDatabase(userEmail: String, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        FirebaseRemote.getInstance().getFirebaseData(
            accessProfileApi(userEmail),
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
        profileData: ProfileData,
        fetchRemote: FetchRemote
    ) {
        insertToUserList(getNewUser(profileData), profileData.useremail,
            firebaseObserver(
                onDataRetrieved = { error ->
                    if (error == null) {
                        try {
                            gamePreference.savePreference(
                                context,
                                GameApi.UserProfile.USER_STORAGE,
                                profileData
                            )

                            fetchRemote.onDataRetrieved?.invoke(null)
                        } catch (ex: Exception) {
                            fetchRemote.onCancelled?.invoke(ex.message)
                        }
                    } else {
                        fetchRemote.onCancelled?.invoke(error as String?)
                    }
                },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() }
            )
        )
    }

    private fun insertToUserList(
        profile: HashMap<String, Any>,
        userEmail: String,
        fetchRemote: FetchRemote
    ) {
        firebaseRemote.insertFirebaseData(
            profile,
            accessProfileApi(userEmail),
            firebaseObserver(
                onDataRetrieved = { error ->
                    if (error == null) {
                        fetchRemote.onDataRetrieved?.invoke(null)
                    } else {
                        fetchRemote.onCancelled?.invoke(error as String?)
                    }
                },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() }
            )
        )
    }

    private fun updateToken(
        context: Context,
        user: ProfileData,
        fetchRemote: FetchRemote
    ) {
        val playerFields = HashMap<String, Any>()

        playerFields[USER_TOKEN] = user.usertoken

        gamePreference.savePreference(
            context,
            GameApi.UserProfile.USER_STORAGE,
            user
        )

        firebaseRemote.insertFirebaseData(
            playerFields,
            accessProfileApi(user.useremail),
            firebaseObserver(
                onDataRetrieved = { error -> fetchRemote.onDataRetrieved?.invoke(error) },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() }
            )
        )
    }

    private fun getNewUser(profileData: ProfileData): HashMap<String, Any> {
        val playerFields = HashMap<String, Any>()

        playerFields[GameApi.UserProfile.Field.USER_NAME] = profileData.username
        playerFields[GameApi.UserProfile.Field.USER_EMAIL] = profileData.useremail
        playerFields[GameApi.UserProfile.Field.USER_IMAGE] = profileData.userimage
        playerFields[GameApi.UserProfile.Field.USER_DATE] = profileData.userdate
        playerFields[GameApi.UserProfile.Field.USER_GAMES] = profileData.usergames
        playerFields[GameApi.UserProfile.Field.USER_RATING] = profileData.userrating
        playerFields[USER_STATE] = profileData.userstate
        playerFields[USER_TOKEN] = profileData.usertoken

        return playerFields
    }

    companion object {
        @Volatile
        private var instance: StartRepository? = null
        fun getInstance(): StartRepository =
            instance ?: synchronized(this) {
                instance ?: StartRepository()
            }.also { instance = it }
    }
}