package com.example.experiments2

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.annotation.Keep
import com.example.experiments2.card.CardData
import com.example.experiments2.card.CardEnum
import kotlin.random.Random.Default.nextInt


@Keep
object Util {
    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getDummyPlayerCard(totalCard: Int): MutableList<CardData> {
        val cardDataList = mutableListOf<CardData>()
        val cardEnumData = CardEnum.values().toList().shuffled()

        for (i in 0..totalCard) {

            val price = when (cardEnumData[i]) {
                CardEnum.MONEY_1 -> 1
                CardEnum.MONEY_2 -> 2
                CardEnum.MONEY_3 -> 3
                CardEnum.MONEY_4 -> 4
                CardEnum.MONEY_5 -> 5
                CardEnum.DEAL_BREAKER -> 4
                CardEnum.SLY_DEAL -> 2
                CardEnum.BIRTHDAY -> 1
                CardEnum.DOUBLE_THE_RENT -> 5
                CardEnum.PASS_GO -> 2
                else -> 0
            }

            val priceList = getPriceListByEnum(cardEnumData[i])
            val newCard = CardData(cardEnumData[i], price, priceList)

            println(newCard)
            cardDataList.add(newCard)
        }

        return cardDataList
    }

    fun getPriceListByEnum(cardEnum: CardEnum): MutableList<Int>? {
        return when(cardEnum) {
            CardEnum.ASSET_A -> mutableListOf(3, 8)
            CardEnum.ASSET_B -> mutableListOf(1, 2, 3)
            CardEnum.ASSET_C -> mutableListOf(2, 4, 7)
            CardEnum.ASSET_D -> mutableListOf(1, 2)
            CardEnum.ASSET_E -> mutableListOf(1, 2, 3, 4)
            else -> null
        }
    }
}
