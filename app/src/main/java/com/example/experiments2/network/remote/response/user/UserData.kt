package com.example.experiments2.network.remote.response.user

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.experiments2.network.remote.response.user.match.MatchData
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserData(
    var profileData: ProfileData? = null,
    var matchData: MutableList<MatchData>? = null
) : Parcelable
