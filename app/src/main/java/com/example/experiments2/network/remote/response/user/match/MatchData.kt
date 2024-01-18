package com.example.experiments2.network.remote.response.user.match

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MatchData(
    var matchPlayer: String = "",
    var matchResult: MatchEnum = MatchEnum.ERROR,
    var matchDate: String = ""
) : Parcelable
