package com.example.experiments2.network.remote.fetch

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query


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

    const val SETTINGS = "settings"
}