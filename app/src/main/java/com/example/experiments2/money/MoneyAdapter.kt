package com.example.experiments2.money

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.R

/**
 * Adapter for the task list. Has a reference to the [TodoListModel] to send actions back to it.
 */
@SuppressLint("NotifyDataSetChanged")
open class MoneyAdapter(
    private var dataset: MutableList<MoneyData>,
): RecyclerView.Adapter<MoneyAdapter.PlayerViewHolder>() {

    var onItemClick: ((MoneyData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_money, parent, false)

        return PlayerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = dataset[position]

        holder.cardIconImage.setImageResource(setMoneyImageByValue(item.money))
        holder.cardIconImage.setOnClickListener { onItemClick?.invoke(item) }

        setToMatchParent(holder.rootMoney)
        setToMatchParent(holder.cardImage)
        setToMatchParent(holder.cardIconImage)
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.iv_money_card)
        val cardIconImage: ImageView = itemView.findViewById(R.id.iv_money_value)
        val rootMoney: ConstraintLayout = itemView.findViewById(R.id.root_money)
    }

    override fun getItemCount() = dataset.size

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

    private fun setToMatchParent(holder: View) {
        val layoutParams = holder.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT

        holder.layoutParams = layoutParams
    }
}