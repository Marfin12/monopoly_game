package com.example.experiments2.network.remote.fetch


object GameApi {
    object UserProfile {
        private const val USER_PROFILE = "user_profile"
        object Field {
            const val USER_NAME = "username"
            const val USER_EMAIL = "useremail"
            const val USER_IMAGE = "userimage"
            const val USER_DATE = "userdate"
            const val USER_GAMES = "usergames"
            const val USER_RATING = "userrating"
            const val USER_TOKEN = "usertoken"
            const val USER_STATE = "userstate"
        }

        fun accessProfileApi(username: String, field: String? = null) : MutableList<String> =
            if (field == null) mutableListOf(USER_PROFILE, username)
            else mutableListOf(USER_PROFILE, username, field)
    }

    const val SETTINGS = "settings"
}