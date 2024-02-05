package com.example.experiments2.network.remote.fetch

import com.example.experiments2.network.FirebaseUtil.getUniqueKey


object GameApi {
    object UserProfile {
        private const val USER_PROFILE = "user_profile"
        private const val USER_MATCH = "user_match"

        object Field {
            const val USER_NAME = "username"
            const val USER_EMAIL = "useremail"
            const val USER_IMAGE = "userimage"
            const val USER_DATE = "userdate"
            const val USER_GAMES = "usergames"
            const val USER_RATING = "userrating"
            const val USER_MATCH = "usermatch"
            const val USER_TOKEN = "usertoken"
            const val USER_STATE = "userstate"
        }

        fun accessProfileApi(userEmail: String? = null, field: String? = null) : MutableList<String> =
            if (userEmail != null)
                if (field == null) mutableListOf(USER_PROFILE, userEmail)
                else mutableListOf(USER_PROFILE, userEmail, field)
            else mutableListOf(USER_PROFILE)
        fun accessMatchApi() = mutableListOf(USER_MATCH)
    }

    object UserRoom {
        const val USER_ROOM = "user_room"
        const val ROOM_PLAYERS = "roomPlayers"

        object Field {
            const val ROOM_ID = "roomId"
            const val ROOM_NAME = "roomName"
            const val ROOM_PLAYERS = "roomPlayers"
            const val ROOM_MODE = "roomMode"
            const val ROOM_MAX_PLAYER = "roomMaxPlayers"
        }

        object Player {
            object Field {
                const val IS_HOST = "isHost"
                const val PLAYER_IMAGE = "playerImage"
            }
        }

        fun accessRoomApi(uniqueKey: String) : MutableList<String> =
            mutableListOf(USER_ROOM, uniqueKey)

        fun accessRoomPlayerApi(roomId: String, username: String): MutableList<String> =
            mutableListOf(USER_ROOM, roomId, ROOM_PLAYERS, username)

    }

    const val SETTINGS = "settings"
}