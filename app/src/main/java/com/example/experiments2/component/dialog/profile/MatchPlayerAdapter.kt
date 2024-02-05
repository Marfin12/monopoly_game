package com.example.experiments2.component.dialog.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.R
import com.example.experiments2.component.label.GameNumberRank
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Adapter for the task list. Has a reference to the [TodoListModel] to send actions back to it.
 */
@SuppressLint("NotifyDataSetChanged")
class MatchPlayerAdapter(
    private var dataset: List<String>,
    private var isProfilePicture: Boolean = false
): RecyclerView.Adapter<MatchPlayerAdapter.PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match_player, parent, false)

        return PlayerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = dataset[position]

        holder.playerNumber.setNumberRank(position + 1)
        holder.tvName.text = item
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerNumber: GameNumberRank = itemView.findViewById(R.id.nr_match_player)
        val tvName: AppCompatTextView = itemView.findViewById(R.id.tv_match_Player)
    }

    override fun getItemCount() = dataset.size
}