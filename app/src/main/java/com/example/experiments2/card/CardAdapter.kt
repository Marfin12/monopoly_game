package com.example.experiments2.card

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.R
import com.example.experiments2.enums.CardEnum

/**
 * Adapter for the task list. Has a reference to the [TodoListModel] to send actions back to it.
 */
@SuppressLint("NotifyDataSetChanged")
open class CardAdapter(
    private var dataset: MutableList<CardData>
): RecyclerView.Adapter<CardAdapter.PlayerViewHolder>() {

    var onItemClick: ((CardData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.money_item, parent, false)

        return PlayerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = dataset[position]

//        holder.cardImage.setImageResource(setMoneyImageByValue(item.money))
        holder.cardImage.setOnClickListener { onItemClick?.invoke(item) }
        if (position >= dataset.size - 1) holder.rootMoney.setPadding(0, 0, 0, 600)
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.iv_money_value)
        val rootMoney: ConstraintLayout = itemView.findViewById(R.id.root_money)
    }

    override fun getItemCount() = dataset.size

    private fun setCardImageByEnum(enum: CardEnum): Int {
        return when (enum) {
            CardEnum.ASSET_A -> R.drawable.asset_card_a
            else -> R.drawable.error
        }
    }

    private fun setMoneyImageByValue(money: Int?): Int {
        return when (money) {
            1 -> R.drawable.money_1k
            2 -> R.drawable.money_2k
            3 -> R.drawable.money_3k
            4 -> R.drawable.money_4k
            5 -> R.drawable.money_5k
            else -> R.drawable.error
        }
    }
}
