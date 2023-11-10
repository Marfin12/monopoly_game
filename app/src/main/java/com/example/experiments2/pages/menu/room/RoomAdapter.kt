package com.example.experiments2.pages.menu.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.MyApplication.Companion.appResources
import com.example.experiments2.R
import org.w3c.dom.Text


@SuppressLint("NotifyDataSetChanged")
open class RoomAdapter(
    private var dataset: MutableList<RoomData>,
): RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    var onItemClick: ((RoomData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)

        return RoomViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val item = dataset[position]

        holder.itemView.updateLayoutParams {
            width = appResources.displayMetrics.widthPixels / 4
        }

        holder.tvTitle.text = item.title
        holder.itemView.setOnClickListener { onItemClick?.invoke(item) }
    }

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_room_title)
    }

    override fun getItemCount() = dataset.size
}