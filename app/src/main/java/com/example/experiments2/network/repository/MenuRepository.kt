package com.example.experiments2.network.repository

import android.content.Context
import android.net.Uri
import com.example.experiments2.MyApplication.Companion.firebaseRemote
import com.example.experiments2.MyApplication.Companion.gamePreference
import com.example.experiments2.constant.Constant.ErrorType.DUPLICATE_USER_ERROR
import com.example.experiments2.constant.Constant.ErrorType.INPUT_SYMBOL_INVALID
import com.example.experiments2.constant.Constant.ErrorType.OPERATION_LOCAL_FAILED
import com.example.experiments2.network.FirebaseUtil.checkToken
import com.example.experiments2.network.FirebaseUtil.firebaseObserver
import com.example.experiments2.network.FirebaseUtil.getDataFromAnyList
import com.example.experiments2.network.remote.fetch.FetchRemote
import com.example.experiments2.network.remote.fetch.FirebaseRemote
import com.example.experiments2.network.remote.fetch.GameApi
import com.example.experiments2.network.remote.fetch.GameApi.UserProfile.Field.USER_NAME
import com.example.experiments2.network.remote.fetch.GameApi.UserProfile.accessProfileApi
import com.example.experiments2.network.remote.response.user.UserData
import com.example.experiments2.network.remote.response.user.match.MatchData
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import com.example.experiments2.pages.menu.MenuData
import com.google.firebase.database.DataSnapshot

class MenuRepository {

    fun saveSetting(context: Context, menuData: MenuData, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        try {
            gamePreference.savePreference(context, GameApi.SETTINGS, menuData)

            fetchRemote.onDataRetrieved?.invoke(null)
        } catch (ex: Exception) {
            fetchRemote.onCancelled?.invoke(ex.message)
        } finally {
            fetchRemote.onComplete?.invoke()
        }
    }

    fun loadSetting(context: Context, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        try {
            val menuData =
                gamePreference.loadPreference<MenuData?>(context, GameApi.SETTINGS) ?: MenuData()

            fetchRemote.onDataRetrieved?.invoke(menuData)
        } catch (ex: Exception) {
            fetchRemote.onCancelled?.invoke(ex.message)
        } finally {
            fetchRemote.onComplete?.invoke()
        }
    }

    fun loadProfile(context: Context, fetchRemote: FetchRemote) {
        fetchRemote.onLoading?.invoke()

        val user = gamePreference.loadPreference<ProfileData?>(
            context, GameApi.UserProfile.Field.USER_EMAIL
        )

        if (user != null) {
            FirebaseRemote.getInstance().getFirebaseData(
                accessProfileApi(user.useremail),
                firebaseObserver(
                    onDataRetrieved = { dataSnapshot ->
                        val snapshot = dataSnapshot as DataSnapshot?

                        if (snapshot != null) {
                            val profile = snapshot.getValue(ProfileData::class.java)

                            if (profile != null) {
                                if (profile.usertoken == user.usertoken) {
                                    val userMatch = profile.usermatch.chunked(6).toMutableList()

                                    loadPlayerMatches(userMatch, firebaseObserver(
                                        onDataRetrieved = { matchesData ->
                                            val matches = matchesData as MutableList<*>
                                            val result = UserData(
                                                profile,
                                                getDataFromAnyList(matches)
                                            )

                                            fetchRemote.onDataRetrieved?.invoke(result)
                                        },
                                        onCancelled = { error ->
                                            fetchRemote.onCancelled?.invoke(error)
                                        },
                                        onComplete = {
                                            fetchRemote.onComplete?.invoke()
                                        },
                                        onLoading = {
                                            fetchRemote.onLoading?.invoke()
                                        }
                                    ))
                                } else forceLogout(fetchRemote)
                            } else {
                                fetchRemote.onCancelled?.invoke(OPERATION_LOCAL_FAILED)
                            }
                        } else {
                            fetchRemote.onCancelled?.invoke(OPERATION_LOCAL_FAILED)
                        }
                    },
                    onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                    onComplete = { fetchRemote.onComplete?.invoke() }
                ))
        } else {
            fetchRemote.onDataRetrieved?.invoke(null)
        }
    }

    fun updateProfileField(
        context: Context,
        field: String,
        value: String,
        fetchRemote: FetchRemote
    ) {
        checkToken(context, firebaseObserver(
            onDataRetrieved = { isValidToken ->
                if (isValidToken == true) {
                    val user = gamePreference.loadPreference<ProfileData?>(
                        context, GameApi.UserProfile.Field.USER_EMAIL
                    )

                    if (user != null) {
                        checkValidityUsername(
                            user.useremail,
                            value,
                            firebaseObserver(
                                onDataRetrieved = { isNotDuplicate ->
                                    if (isNotDuplicate == true) {
                                        val playerFields = HashMap<String, Any>()

                                        playerFields[field] = value
                                        firebaseRemote.insertFirebaseData(
                                            playerFields,
                                            accessProfileApi(user.useremail),
                                            firebaseObserver(
                                                onDataRetrieved = { errorInsert ->
                                                    fetchRemote.onDataRetrieved?.invoke(errorInsert)
                                                },
                                                onCancelled = { errorInsert ->
                                                    fetchRemote.onCancelled?.invoke(errorInsert)
                                                },
                                                onComplete = { fetchRemote.onComplete?.invoke() },
                                                onLoading = { fetchRemote.onLoading?.invoke() }
                                            )
                                        )
                                    } else {
                                        fetchRemote.onCancelled?.invoke(DUPLICATE_USER_ERROR)
                                    }
                                },
                                onCancelled = { errorInsert ->
                                    fetchRemote.onCancelled?.invoke(
                                        errorInsert
                                    )
                                },
                                onComplete = { fetchRemote.onComplete?.invoke() },
                                onLoading = { fetchRemote.onLoading?.invoke() }
                            )
                        )
                    } else {
                        forceLogout(fetchRemote)
                    }
                } else {
                    forceLogout(fetchRemote)
                }
            },
            onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
            onComplete = { fetchRemote.onComplete?.invoke() },
            onLoading = { fetchRemote.onLoading?.invoke() }
        ))
    }

    private fun checkValidityUsername(
        userId: String,
        value: String,
        fetchRemote: FetchRemote
    ) {
        if (!value.matches(Regex("^[^,'><!#$%&|\\[\\]{}()]*$"))) {
            fetchRemote.onCancelled?.invoke(INPUT_SYMBOL_INVALID)
            fetchRemote.onComplete?.invoke()
        } else {
            FirebaseRemote.getInstance().getFirebaseData(
                accessProfileApi(),
                firebaseObserver(
                    onDataRetrieved = { snapshot ->
                        val dataSnapshot = snapshot as DataSnapshot?

                        if (dataSnapshot != null) {
                            val iterator = dataSnapshot.children.iterator()
                            var isSearching = true

                            while (iterator.hasNext() && isSearching) {
                                val userSnapshot = iterator.next()
                                val snapshotKey = userSnapshot.key
                                val username = userSnapshot.child(USER_NAME).getValue(String::class.java) ?: ""

                                if (userId != snapshotKey && username == value) {
                                    isSearching = false
                                }
                            }

                            fetchRemote.onDataRetrieved?.invoke(isSearching)
                        } else {
                            fetchRemote.onCancelled?.invoke(OPERATION_LOCAL_FAILED)
                        }
                    },
                    onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                    onComplete = { fetchRemote.onComplete?.invoke() },
                )
            )
        }
    }

    private fun loadPlayerMatches(matchesId: MutableList<String>, fetchRemote: FetchRemote) {
        val listMatch = mutableListOf<MatchData>()

        FirebaseRemote.getInstance().getFirebaseData(
            GameApi.UserProfile.accessMatchApi(),
            firebaseObserver(
                onDataRetrieved = { dataSnapshot ->
                    val snapshot = dataSnapshot as DataSnapshot?

                    if (snapshot != null) {
                        for (postSnapshot in snapshot.children) {
                            val match = postSnapshot.getValue(MatchData::class.java)

                            if (match != null && matchesId.contains(postSnapshot.key)) {
                                listMatch.add(match)
                            }

                            fetchRemote.onDataRetrieved?.invoke(listMatch)
                        }
                    }
                },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() },
                onLoading = { fetchRemote.onLoading?.invoke() }
            )
        )
    }

    fun uploadPhotoProfile(context: Context, uri: Uri, fetchRemote: FetchRemote) {
        checkToken(context, firebaseObserver(
            onDataRetrieved = { isValidToken ->
                if (isValidToken == true) {
                    val user = gamePreference.loadPreference<ProfileData?>(
                        context, GameApi.UserProfile.Field.USER_EMAIL
                    )

                    if (user != null) {
                        firebaseRemote.uploadPhoto(uri, user.useremail, firebaseObserver(
                            onDataRetrieved = { uri ->
                                fetchRemote.onDataRetrieved?.invoke(uri)
                            },
                            onCancelled = { error ->
                                fetchRemote.onCancelled?.invoke(error)
                            },
                            onComplete = {
                                fetchRemote.onComplete?.invoke()
                            },
                            onLoading = { fetchRemote.onLoading?.invoke() }
                        ))
                    } else {
                        forceLogout(fetchRemote)
                    }
                } else {
                    forceLogout(fetchRemote)
                }
            },
            onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
            onComplete = { fetchRemote.onComplete?.invoke() },
            onLoading = { fetchRemote.onLoading?.invoke() }
        ))
    }

    private fun forceLogout(fetchRemote: FetchRemote) {
        fetchRemote.onDataRetrieved?.invoke(null)
        fetchRemote.onComplete?.invoke()

        firebaseRemote.logoutGoogle(fetchRemote)
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