package com.example.experiments2.component.dialog.profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.R
import com.example.experiments2.component.label.GameNumberRank
import com.example.experiments2.money.MoneyAdapter
import com.example.experiments2.money.MoneyData
import com.example.experiments2.network.remote.response.user.match.MatchData
import com.example.experiments2.network.remote.response.user.match.MatchEnum
import com.example.experiments2.util.Util.getString
import kotlin.math.abs

/**
 * Adapter for the task list. Has a reference to the [TodoListModel] to send actions back to it.
 */
@SuppressLint("NotifyDataSetChanged")
class MatchAdapter(
    private var dataset: MutableList<MatchData>,
    private var context: Context
): RecyclerView.Adapter<MatchAdapter.PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)

        return PlayerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = dataset[position]

        holder.tvMatchDate.text = item.matchDate

        holder.rvPlayer.layoutManager = LinearLayoutManager(context)
        holder.rvPlayer.adapter = MatchPlayerAdapter(item.matchPlayer.split(","))

        setResultLogo(holder.ivLogoIcon, holder.ivLogoWording, item.matchResult)
        setMatchPoint(holder.tvMatchResult, item.matchResult)
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvPlayer: RecyclerView = itemView.findViewById(R.id.rv_match_player_rank)
        val ivLogoIcon: AppCompatImageView = itemView.findViewById(R.id.iv_match_result_logo_1)
        val ivLogoWording: AppCompatImageView = itemView.findViewById(R.id.iv_match_result_logo_2)
        val tvMatchResult: AppCompatTextView = itemView.findViewById(R.id.tv_match_result_logo)
        val tvMatchDate: AppCompatTextView = itemView.findViewById(R.id.tv_match_result_date)
    }

    private fun setResultLogo(
        ivLogoIcon: AppCompatImageView,
        ivLogoWording: AppCompatImageView,
        matchEnum: MatchEnum
    ) {
        when(matchEnum) {
            MatchEnum.WIN -> {
                ivLogoIcon.setImageResource(R.drawable.game_win_logo)
                ivLogoWording.setImageResource(R.drawable.win_word)
            }
            MatchEnum.RICH -> {
                ivLogoIcon.setImageResource(R.drawable.game_rich_logo)
                ivLogoWording.setImageResource(R.drawable.rich_word)
            }
            MatchEnum.LOSE -> {
                ivLogoIcon.setImageResource(R.drawable.lose_word)
                ivLogoWording.visibility = View.GONE
            }
            else -> {
                ivLogoIcon.visibility = View.GONE
                ivLogoWording.visibility = View.GONE
            }
        }
    }

    private fun setMatchPoint(tvMatchResult: AppCompatTextView, matchEnum: MatchEnum) {
        val point = getMatchPoint(matchEnum.ordinal)

        if (point > 0) {
            tvMatchResult.text = getString(
                context,
                R.string.menu_profile_total_match_point_plus,
                getMatchPoint(matchEnum.ordinal)
            )
        } else if (point < 0) {
            tvMatchResult.text = getString(
                context,
                R.string.menu_profile_total_match_point_minus,
                abs(getMatchPoint(matchEnum.ordinal))
            )
        }
        else {
            tvMatchResult.text = "0"
        }
    }

    private fun getMatchPoint(matchOrdinal: Int) : Int = 15 - (matchOrdinal * 10)

    override fun getItemCount() = dataset.size
}