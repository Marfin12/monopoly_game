package com.example.experiments2.card

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CardData(
    val cardEnum: CardEnum = CardEnum.NO_CARD,
    val cardPrice: Int = 0,
    var assetPriceList: MutableList<Int>? = null
) : Parcelable
