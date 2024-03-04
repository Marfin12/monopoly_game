package com.example.experiments2.network.repository

import android.content.Context
import com.example.experiments2.MyApplication.Companion.firebaseRemote
import com.example.experiments2.MyApplication.Companion.gamePreference
import com.example.experiments2.constant.Constant.AI_BOTS
import com.example.experiments2.constant.Constant.ErrorType.OPERATION_LOCAL_FAILED
import com.example.experiments2.network.FirebaseUtil
import com.example.experiments2.network.FirebaseUtil.checkToken
import com.example.experiments2.network.FirebaseUtil.firebaseObserver
import com.example.experiments2.network.FirebaseUtil.forceLogout
import com.example.experiments2.network.remote.fetch.FetchRemote
import com.example.experiments2.network.remote.fetch.GameApi
import com.example.experiments2.network.remote.fetch.GameApi.UserRoom.accessRoomApi
import com.example.experiments2.network.remote.fetch.GameApi.UserRoom.accessRoomPlayerApi
import com.example.experiments2.network.remote.response.game.player.PlayerData
import com.example.experiments2.network.remote.response.game.room.RoomData
import com.example.experiments2.network.remote.response.game.room.RoomEnum
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import com.example.experiments2.pages.menu.fragments.CreateJoinData
import com.google.firebase.database.DataSnapshot

class RoomRepository {

    private var roomPlayers: HashMap<String, PlayerData> = hashMapOf()

    fun leaveRoom(
        context: Context,
        roomId: String,
        fetchRemote: FetchRemote = firebaseObserver(),
        callback: (() -> Unit)? = null
    ) {
        fetchRemote.onLoading?.invoke()

        val user = gamePreference.loadPreference<ProfileData?>(
            context, GameApi.UserProfile.USER_STORAGE
        )

        if (user != null) {
            val apiAccess = if (roomPlayers.size > 1) accessRoomPlayerApi(roomId, user.username)
            else accessRoomApi(roomId)

            firebaseRemote.removeFirebaseData(apiAccess, firebaseObserver(
                onDataRetrieved = { errorRemove ->
                    fetchRemote.onDataRetrieved?.invoke(errorRemove)
                    callback?.invoke()
                },
                onCancelled = { error ->
                    fetchRemote.onCancelled?.invoke(error)
                },
                onComplete = {
                    fetchRemote.onComplete?.invoke()
                }
            ))
        } else {
            forceLogout(fetchRemote)
        }
    }

    fun hostRoom(
        context: Context,
        roomName: String,
        roomMode: RoomEnum,
        maxPlayer: Int,
        fetchRemote: FetchRemote
    ) {
        fetchRemote.onLoading?.invoke()

        val user = gamePreference.loadPreference<ProfileData?>(
            context, GameApi.UserProfile.USER_STORAGE
        )

        if (user != null) {
            if (roomMode == RoomEnum.ONLINE) {
                val roomId = FirebaseUtil.getUniqueKey(GameApi.UserRoom.USER_ROOM)
                val roomData = RoomData(roomId, roomName, roomMode, maxPlayer)
                val data = getRoomFieldOnline(
                    roomData,
                    user.username,
                    user.userimage
                )

                firebaseRemote.insertFirebaseData(
                    data,
                    accessRoomApi(roomId),
                    firebaseObserver(
                        onDataRetrieved = { errorInsert ->
                            if (errorInsert == null) {
                                joinGameRoom(
                                    context,
                                    roomId,
                                    firebaseObserver(
                                        onLoading = { fetchRemote.onLoading?.invoke() },
                                        onDataRetrieved = {
                                            joinGameRoom(context, roomId, fetchRemote)
                                        },
                                        onCancelled = { error ->
                                            fetchRemote.onCancelled?.invoke(error)
                                        },
                                        onComplete = {
                                            fetchRemote.onComplete?.invoke()
                                        }
                                    )
                                )
                            } else {
                                fetchRemote.onCancelled?.invoke(errorInsert as String?)
                            }
                        },
                        onCancelled = { errorInsert ->
                            fetchRemote.onCancelled?.invoke(errorInsert)
                        },
                        onComplete = { fetchRemote.onComplete?.invoke() },
                        onLoading = { fetchRemote.onLoading?.invoke() }
                    )
                )
            } else {
                fetchRemote.onDataRetrieved?.invoke(
                    CreateJoinData(
                        RoomData(
                            "", roomName, roomMode, maxPlayer, getRoomFieldBots(
                                user.username,
                                user.userimage,
                                maxPlayer
                            )
                        )
                    )
                )
                fetchRemote.onComplete?.invoke()
            }
        }
    }

    fun joinGameRoom(
        context: Context,
        roomId: String,
        fetchRemote: FetchRemote,
        isListening: Boolean = true
    ) {
        fetchRemote.onLoading?.invoke()

        val user = gamePreference.loadPreference<ProfileData?>(
            context, GameApi.UserProfile.USER_STORAGE
        )

        if (user != null) {
            if (isListening) {
                firebaseRemote.listenFirebaseData(
                    accessRoomApi(roomId),
                    firebaseObserver(
                        onDataRetrieved = { snapshot ->
                            val dataSnapshot = snapshot as DataSnapshot
                            fetchRoomData(context, dataSnapshot, roomId, fetchRemote)
                        },
                        onCancelled = { errorInsert ->
                            fetchRemote.onCancelled?.invoke(errorInsert)
                        }
                    ),
                )
            } else {
                firebaseRemote.getFirebaseData(
                    accessRoomApi(roomId),
                    firebaseObserver(
                        onDataRetrieved = { snapshot ->
                            val dataSnapshot = snapshot as DataSnapshot
                            fetchRoomData(context, dataSnapshot, roomId, fetchRemote)
                        },
                        onCancelled = { errorInsert ->
                            fetchRemote.onCancelled?.invoke(errorInsert)
                        }
                    ),
                )
            }
        }
    }

    private fun fetchRoomData(
        context: Context,
        snapshot: DataSnapshot?,
        roomId: String,
        fetchRemote: FetchRemote
    ) {
        checkToken(
            context, firebaseObserver(
                onDataRetrieved = { isValidToken ->
                    if (isValidToken == true) {
                        if (snapshot != null) {
                            val roomData = snapshot.getValue(RoomData::class.java)

                            if (roomData != null) {
                                fetchRemote.onDataRetrieved?.invoke(CreateJoinData(roomData))

                                this.roomPlayers = roomData.roomPlayers
                            } else {
                                fetchRemote.onCancelled?.invoke(
                                    OPERATION_LOCAL_FAILED
                                )
                            }
                        }
                    } else {
                        leaveRoom(context, roomId, firebaseObserver()) {
                            forceLogout(fetchRemote)
                        }
                    }
                },
                onCancelled = { error -> fetchRemote.onCancelled?.invoke(error) },
                onComplete = { fetchRemote.onComplete?.invoke() },
            )
        )
    }

    private fun getRoomFieldOnline(
        room: RoomData,
        username: String,
        userImage: String
    ): HashMap<String, Any> {
        val roomField = HashMap<String, Any>()
        val userField = HashMap<String, Any>()
        val playerField = HashMap<String, Any>()

        playerField[GameApi.UserRoom.Player.Field.IS_HOST] = true
        playerField[GameApi.UserRoom.Player.Field.PLAYER_IMAGE] = userImage

        userField[username] = playerField

        roomField[GameApi.UserRoom.Field.ROOM_ID] = room.roomId
        roomField[GameApi.UserRoom.Field.ROOM_NAME] = room.roomName
        roomField[GameApi.UserRoom.Field.ROOM_MODE] = room.roomMode
        roomField[GameApi.UserRoom.Field.ROOM_MAX_PLAYER] = room.roomMaxPlayers
        roomField[GameApi.UserRoom.Field.ROOM_PLAYERS] = userField

        return roomField
    }

    private fun getRoomFieldBots(
        username: String,
        userImage: String,
        maxPlayer: Int
    ): HashMap<String, PlayerData> {
        val playerData = hashMapOf<String, PlayerData>()

        val botsPlayer = AI_BOTS.shuffled().take(maxPlayer - 1)

        playerData[username] = PlayerData(true, userImage)

        botsPlayer.forEach { playerBots ->
            playerData[playerBots] = PlayerData(false, "")
        }

        return playerData
    }

    companion object {
        @Volatile
        private var instance: RoomRepository? = null
        fun getInstance(): RoomRepository =
            instance ?: synchronized(this) {
                instance ?: RoomRepository()
            }.also { instance = it }
    }
}