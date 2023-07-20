package com.example.experiments2.card

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.R


@SuppressLint("NotifyDataSetChanged")
open class CardAdapter(
    private var dataset: MutableList<CardData>, private var context: Context
): RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    var onItemClick: ((CardData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)

        return CardViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = dataset[position]

        if (item.assetPriceList != null) generateAssetCardType(item, holder, context)
        else generateNonAssetCardType(item, holder)

        holder.cardImage.setOnClickListener { onItemClick?.invoke(item) }
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.iv_card)
        val rootAsset: ConstraintLayout = itemView.findViewById(R.id.root_asset_card)
        val rootMoney: ConstraintLayout = itemView.findViewById(R.id.root_money_card)
    }

    override fun getItemCount() = dataset.size

    private fun generateAssetCardType(
        item: CardData,
        cardViewHolder: CardViewHolder,
        context: Context
    ) {
        cardViewHolder.cardImage.visibility = View.GONE
        cardViewHolder.rootAsset.visibility = View.VISIBLE
        cardViewHolder.rootAsset.visibility = View.VISIBLE

        val listPrice = cardViewHolder.rootAsset.findViewById<RecyclerView>(R.id.rv_list_price)

        val priceAdapter = when (item.cardEnum) {
            CardEnum.ASSET_A -> PriceAdapter(item.assetPriceList!!,R.drawable.blue_b_card)
            CardEnum.ASSET_B -> PriceAdapter(item.assetPriceList!!,R.drawable.white_b_card)
            CardEnum.ASSET_C -> PriceAdapter(item.assetPriceList!!,R.drawable.green_b_card)
            CardEnum.ASSET_D -> PriceAdapter(item.assetPriceList!!,R.drawable.red_b_card)
            CardEnum.ASSET_E -> PriceAdapter(item.assetPriceList!!,R.drawable.yellow_b_card)
            else -> PriceAdapter(mutableListOf(), R.drawable.error)
        }

        listPrice.adapter = priceAdapter
        listPrice.layoutManager = LinearLayoutManager(context)
    }

    private fun generateNonAssetCardType(item: CardData, cardViewHolder: CardViewHolder) {
        cardViewHolder.cardImage.visibility = View.VISIBLE
        cardViewHolder.rootAsset.visibility = View.GONE
        cardViewHolder.rootMoney.visibility = View.GONE

        when (item.cardEnum) {
            CardEnum.BIRTHDAY ->
                cardViewHolder.cardImage.setImageResource(R.drawable.action_card_birthday)
            CardEnum.DEBT_COLLECTOR ->
                cardViewHolder.cardImage.setImageResource(R.drawable.action_card_debt_collector)
            CardEnum.DOUBLE_THE_RENT ->
                cardViewHolder.cardImage.setImageResource(R.drawable.action_card_double_the_rent)
            CardEnum.FORCED_DEAL ->
                cardViewHolder.cardImage.setImageResource(R.drawable.action_card_forced_deal)
            CardEnum.PASS_GO ->
                cardViewHolder.cardImage.setImageResource(R.drawable.action_card_pass_go)
            CardEnum.SAY_NO ->
                cardViewHolder.cardImage.setImageResource(R.drawable.action_card_say_no)
            CardEnum.SLY_DEAL ->
                cardViewHolder.cardImage.setImageResource(R.drawable.action_card_sly_deal)
            CardEnum.DEAL_BREAKER ->
                cardViewHolder.cardImage.setImageResource(R.drawable.action_deal_breaker)
            CardEnum.RENT_CARD_A ->
                cardViewHolder.cardImage.setImageResource(R.drawable.asset_card_a)
            CardEnum.RENT_CARD_B ->
                cardViewHolder.cardImage.setImageResource(R.drawable.asset_card_b)
            CardEnum.RENT_CARD_C ->
                cardViewHolder.cardImage.setImageResource(R.drawable.asset_card_c)
            CardEnum.RENT_CARD_D ->
                cardViewHolder.cardImage.setImageResource(R.drawable.asset_card_d)
            CardEnum.RENT_CARD_E ->
                cardViewHolder.cardImage.setImageResource(R.drawable.asset_card_e)
            else -> {
                cardViewHolder.cardImage.visibility = View.GONE
                cardViewHolder.rootAsset.visibility = View.GONE
                cardViewHolder.rootMoney.visibility = View.VISIBLE

                val moneyIcon = cardViewHolder.rootMoney.findViewById<ImageView>(R.id.iv_money_value)

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
}
