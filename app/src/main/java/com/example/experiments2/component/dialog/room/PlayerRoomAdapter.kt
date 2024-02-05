package com.example.experiments2.component.dialog.room

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.experiments2.R
import com.example.experiments2.component.label.GameNumberRank
import com.example.experiments2.network.remote.response.game.player.PlayerData
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Adapter for the task list. Has a reference to the [TodoListModel] to send actions back to it.
 */
@SuppressLint("NotifyDataSetChanged")
class PlayerRoomAdapter(
    private var dataset: HashMap<String, PlayerData>,
    private var context: Context
): RecyclerView.Adapter<PlayerRoomAdapter.PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room_player, parent, false)

        return PlayerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val itemKey = dataset.keys.toMutableList()[position]
        val itemPlayer = dataset.values.toMutableList()[position]

        holder.tvName.text = itemKey
        if (itemPlayer.playerImage != "") {
            Glide.with(context)
                .load(itemPlayer.playerImage)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)) // Caching options
                .into(holder.profilePic)
        }

        if (itemPlayer.isHost) {
            holder.tvName.setTextColor(
                context.resources.getColor(R.color.aqua_200, context.theme)
            )
        }
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profilePic: CircleImageView = itemView.findViewById(R.id.iv_profile_new)
        val tvName: AppCompatTextView = itemView.findViewById(R.id.tv_match_Player)
    }

    override fun getItemCount() = dataset.size
}