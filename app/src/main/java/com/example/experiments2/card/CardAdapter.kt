package com.example.experiments2.card

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.R
import com.example.experiments2.util.CardUtil.generateAssetCardType
import com.example.experiments2.util.CardUtil.generateNonAssetCardType


@SuppressLint("NotifyDataSetChanged")
open class CardAdapter(
    private var dataset: MutableList<CardData>, private var context: Context
): RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    var onAssetItemClick: ((CardData, ImageView) -> Unit)? = null
    var onNonAssetItemClick: ((CardData, ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)

        return CardViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = dataset[position]

        if (item.assetPriceList != null) {
            holder.rootParent.setOnClickListener {
                onAssetItemClick?.invoke(item, holder.cardImage)
            }

            generateAssetCardType(item, holder.cardImage, holder.rootAsset, holder.rootMoney, context)
        }
        else {
            holder.rootParent.setOnClickListener {
                onNonAssetItemClick?.invoke(item, holder.cardImage)
            }

            generateNonAssetCardType(item, holder.cardImage, holder.rootAsset, holder.rootMoney)
        }
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootParent: ConstraintLayout = itemView.findViewById(R.id.root_parent_card)
        val cardImage: ImageView = itemView.findViewById(R.id.iv_card)
        val rootAsset: ConstraintLayout = itemView.findViewById(R.id.root_asset_card)
        val rootMoney: ConstraintLayout = itemView.findViewById(R.id.root_money_card)
    }

    override fun getItemCount() = dataset.size
}
