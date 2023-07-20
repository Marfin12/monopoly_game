package com.example.experiments2.asset

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.experiments2.card.CardEnum
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AssetData(
    val cardEnum: CardEnum = CardEnum.NO_CARD
) : Parcelable
