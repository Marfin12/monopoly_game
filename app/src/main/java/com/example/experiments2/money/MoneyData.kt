package com.example.experiments2.money

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MoneyData(
    val money: Int? = -1
) : Parcelable