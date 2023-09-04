package com.example.experiments2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.experiments2.card.CardData
import com.example.experiments2.card.CardEnum
import com.example.experiments2.card.PriceAdapter


@Keep
object Util {
    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun playGif(
        context: Context,
        gifResource: Int,
        targetView: ImageView,
        totalLoop: Int = 0,
        onAnimationEnd: (() -> Unit)? = null
    ) {
        Glide.with(context)
            .asGif()
            .transition(withCrossFade())
            .load(gifResource)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (totalLoop > 0) resource?.setLoopCount(totalLoop)
                    resource?.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable) {
                            onAnimationEnd?.invoke()

                            super.onAnimationEnd(drawable)
                        }
                    })
                    return false
                }
            })
            .into(targetView)
    }

    fun getDummyPlayerCard(totalCard: Int): MutableList<CardData> {
        val cardDataList = mutableListOf<CardData>()
        val selectedCardEnum = CardEnum.values().toList()
        val cardEnumData = selectedCardEnum.slice(1 until selectedCardEnum.size)

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

            cardDataList.add(newCard)
        }

        return cardDataList
    }

    fun generateAssetCardType(
        item: CardData,
        cardImageView: ImageView,
        rootAsset: ConstraintLayout,
        rootMoney: ConstraintLayout,
        context: Context
    ) {
        val priceRecyclerView = rootAsset.findViewById<RecyclerView>(R.id.rv_list_price)

        cardImageView.visibility = View.GONE
        rootMoney.visibility = View.GONE
        rootAsset.visibility = View.VISIBLE

        val priceAdapter = when (item.cardEnum) {
            CardEnum.ASSET_A -> PriceAdapter(item.assetPriceList!!, R.drawable.blue_b_card)
            CardEnum.ASSET_B -> PriceAdapter(item.assetPriceList!!, R.drawable.white_b_card)
            CardEnum.ASSET_C -> PriceAdapter(item.assetPriceList!!, R.drawable.green_b_card)
            CardEnum.ASSET_D -> PriceAdapter(item.assetPriceList!!, R.drawable.red_b_card)
            CardEnum.ASSET_E -> PriceAdapter(item.assetPriceList!!, R.drawable.yellow_b_card)
            else -> PriceAdapter(mutableListOf(), R.drawable.error)
        }

        priceRecyclerView.adapter = priceAdapter
        priceRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun generateNonAssetCardType(
        item: CardData,
        cardImageView: ImageView,
        rootAsset: ConstraintLayout,
        rootMoney: ConstraintLayout
    ) {
        cardImageView.visibility = View.VISIBLE
        rootAsset.visibility = View.GONE
        rootMoney.visibility = View.GONE

        when (item.cardEnum) {
            CardEnum.BIRTHDAY ->
                cardImageView.setImageResource(R.drawable.action_card_birthday)
            CardEnum.DEBT_COLLECTOR ->
                cardImageView.setImageResource(R.drawable.action_card_debt_collector)
            CardEnum.DOUBLE_THE_RENT ->
                cardImageView.setImageResource(R.drawable.action_card_double_the_rent)
            CardEnum.FORCED_DEAL ->
                cardImageView.setImageResource(R.drawable.action_card_forced_deal)
            CardEnum.PASS_GO ->
                cardImageView.setImageResource(R.drawable.action_card_pass_go)
            CardEnum.SAY_NO ->
                cardImageView.setImageResource(R.drawable.action_card_say_no)
            CardEnum.SLY_DEAL ->
                cardImageView.setImageResource(R.drawable.action_card_sly_deal)
            CardEnum.DEAL_BREAKER ->
                cardImageView.setImageResource(R.drawable.action_deal_breaker)
            CardEnum.RENT_CARD_A ->
                cardImageView.setImageResource(R.drawable.asset_card_a)
            CardEnum.RENT_CARD_B ->
                cardImageView.setImageResource(R.drawable.asset_card_b)
            CardEnum.RENT_CARD_C ->
                cardImageView.setImageResource(R.drawable.asset_card_c)
            CardEnum.RENT_CARD_D ->
                cardImageView.setImageResource(R.drawable.asset_card_d)
            CardEnum.RENT_CARD_E ->
                cardImageView.setImageResource(R.drawable.asset_card_e)
            else -> {
                cardImageView.visibility = View.GONE
                rootAsset.visibility = View.GONE
                rootMoney.visibility = View.VISIBLE

                val moneyIcon = rootMoney.findViewById<ImageView>(R.id.iv_money_value)

                when (item.cardEnum) {
                    CardEnum.MONEY_1 -> moneyIcon.setImageResource(R.drawable.money_1k)
                    CardEnum.MONEY_2 -> moneyIcon.setImageResource(R.drawable.money_2k)
                    CardEnum.MONEY_3 -> moneyIcon.setImageResource(R.drawable.money_3k)
                    CardEnum.MONEY_4 -> moneyIcon.setImageResource(R.drawable.money_4k)
                    CardEnum.MONEY_5 -> moneyIcon.setImageResource(R.drawable.money_5k)
                    else -> moneyIcon.setImageResource(R.drawable.error)
                }
            }
        }
    }

    private fun getPriceListByEnum(cardEnum: CardEnum): MutableList<Int>? {
        return when (cardEnum) {
            CardEnum.ASSET_A -> mutableListOf(3, 8)
            CardEnum.ASSET_B -> mutableListOf(1, 2, 3)
            CardEnum.ASSET_C -> mutableListOf(2, 4, 7)
            CardEnum.ASSET_D -> mutableListOf(1, 2)
            CardEnum.ASSET_E -> mutableListOf(1, 2, 3, 4)
            else -> null
        }
    }
}
