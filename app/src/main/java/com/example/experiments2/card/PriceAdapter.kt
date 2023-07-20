package com.example.experiments2.card

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.R


@SuppressLint("NotifyDataSetChanged")
open class PriceAdapter(
    private var dataset: MutableList<Int>, var imageResourceId: Int
): RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.asset_price_item, parent, false)

        return PriceViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val item = dataset[position]

        holder.cardImage.setImageResource(imageResourceId)
        holder.assetPrice.text = item.toString()
        holder.rootItem.setOnClickListener { onItemClick?.invoke(item) }
    }

    inner class PriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootItem: ConstraintLayout = itemView.findViewById(R.id.root_price)
        val cardImage: ImageView = itemView.findViewById(R.id.iv_building_icon)
        val assetPrice: TextView = itemView.findViewById(R.id.tv_asset_price)
    }

    override fun getItemCount() = dataset.size
}