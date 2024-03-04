package com.example.experiments2.pages.menu.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.experiments2.network.FirebaseUtil
import com.example.experiments2.network.remote.fetch.FetchRemote
import com.example.experiments2.network.remote.response.game.room.RoomData
import com.example.experiments2.network.remote.response.game.room.RoomEnum
import com.example.experiments2.network.repository.RoomRepository
import com.example.experiments2.viewmodel.ViewModelBase
import com.example.experiments2.viewmodel.ViewModelData
import com.example.experiments2.viewmodel.ViewModelEnum
import java.io.InputStream


class CreateJoinViewModel :
    ViewModelBase<CreateJoinData, RoomRepository>(RoomRepository.getInstance()) {
    fun hostTheRoom(
        context: Context,
        roomName: String,
        roomMode: RoomEnum,
        maxPlayer: Int
    ) {
        var isCreating = true

        repository.hostRoom(context, roomName, roomMode, maxPlayer, FirebaseUtil.firebaseObserver(
            onDataRetrieved = { data ->
                if (data is CreateJoinData) {
                    data.state = if (isCreating) CreateJoinEnum.CREATE
                    else CreateJoinEnum.LISTEN

                    _vmData.value = ViewModelData(ViewModelEnum.SUCCESS, data)
                    isCreating = false
                } else {
                    _vmData.value = ViewModelData(ViewModelEnum.SUCCESS, CreateJoinData(
                        state = CreateJoinEnum.FORCE_LOGOUT
                    ))
                }
            },
            onCancelled = { error -> this.onError(error) },
            onLoading = { this.onLoading() },
            onComplete = { this.onComplete(null) }
        ))
    }

    fun leaveTheRoom(context: Context, roomId: String) {
        repository.leaveRoom(context, roomId, FirebaseUtil.firebaseObserver(
            onDataRetrieved = { _ ->
                _vmData.value = ViewModelData(
                    ViewModelEnum.SUCCESS,
                    CreateJoinData(state = CreateJoinEnum.LEAVE)
                )
            },
            onCancelled = { error -> this.onError(error) },
            onLoading = {
                _vmData.value = ViewModelData(
                    ViewModelEnum.LOADING,
                    CreateJoinData()
                )
            },
            onComplete = { this.onComplete(null) }
        ))
    }

    fun refreshTheRoom(context: Context, roomId: String) {
        repository.joinGameRoom(context, roomId, FirebaseUtil.firebaseObserver(
            onDataRetrieved = { data ->
                if (data is CreateJoinData) {
                    data.state = CreateJoinEnum.LISTEN

                    _vmData.value = ViewModelData(ViewModelEnum.SUCCESS, data)
                } else {
                    _vmData.value = ViewModelData(ViewModelEnum.SUCCESS, CreateJoinData(
                        state = CreateJoinEnum.FORCE_LOGOUT
                    ))
                }
            },
            onCancelled = { error -> this.onError(error) },
            onLoading = { this.onLoading() },
            onComplete = { this.onComplete(null) }
        ), false)
    }
}