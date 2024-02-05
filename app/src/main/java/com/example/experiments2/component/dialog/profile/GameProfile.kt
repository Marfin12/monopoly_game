package com.example.experiments2.component.dialog.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.experiments2.R
import com.example.experiments2.component.dialog.GameDialog
import com.example.experiments2.component.label.GameNormalLabel
import com.example.experiments2.network.remote.response.user.UserData
import com.example.experiments2.network.remote.response.user.profile.ProfileData
import com.example.experiments2.util.Util.getString
import com.example.experiments2.util.Util.toDisable
import de.hdodenhof.circleimageview.CircleImageView


class GameProfile(context: Context) : GameDialog(context) {
    private lateinit var ivCancel: AppCompatImageView
    private lateinit var ivProfileImage: CircleImageView
    private lateinit var ivEditUsername: AppCompatImageView
    private lateinit var ivDoneUsername: AppCompatImageView

    private lateinit var etUsername: AppCompatEditText

    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDate: AppCompatTextView
    private lateinit var tvGames: AppCompatTextView
    private lateinit var tvRating: AppCompatTextView

    private lateinit var btnChange: GameNormalLabel

    private lateinit var sectionMatch: ConstraintLayout
    private lateinit var rvMatch: RecyclerView

    private lateinit var progressBar: ProgressBar
    private lateinit var viewTransparent: View
    private lateinit var scrollView: MyScrollView

    var onDoneEditUsername: ((String) -> Unit)? = null
    var onChangePhoto: (() -> Unit)? = null

    private var theField: String = ""

    companion object {
        fun newInstance(context: Context): GameProfile {
            return GameProfile(context)
        }
    }

    override fun initDialog(layoutId: Int?, styleId: Int?) {
        super.initDialog(R.layout.component_transfloat_profile, R.style.ProfileDialog)

        ivCancel = view.findViewById(R.id.iv_cancel)
        ivProfileImage = view.findViewById(R.id.iv_profile_new)
        ivEditUsername = view.findViewById(R.id.iv_profile_edit_username)
        ivDoneUsername = view.findViewById(R.id.iv_profile_done_username)

        etUsername = view.findViewById(R.id.et_profile_username)

        tvTitle = view.findViewById(R.id.tv_message_title)
        tvDate = view.findViewById(R.id.tv_profile_date)
        tvGames = view.findViewById(R.id.tv_profile_total_game)
        tvRating = view.findViewById(R.id.tv_profile_rating)

        btnChange = view.findViewById(R.id.lbl_holder_name_bg)

        sectionMatch = view.findViewById(R.id.section_profile_match_history)

        progressBar = view.findViewById(R.id.progress_bar)
        viewTransparent = view.findViewById(R.id.view_transparant)
        rvMatch = view.findViewById(R.id.rv_list_match)
        scrollView = view.findViewById(R.id.scrollView)

        initComponent()
    }

    fun show(userData: UserData?) {
        dialog.setOnDismissListener {
            loseFocus()
        }
        dialog.show()

        if (userData != null) initData(userData)
        else {
            ivEditUsername.toDisable()
            ivDoneUsername.toDisable()
        }
    }

    fun previewImage(uri: Uri) {
        Glide.with(context)
            .load(uri)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .listener(object : com.bumptech.glide.request.RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    hideLoading()
                    return false
                }
            })
            .into(ivProfileImage)
    }

    fun onUpdateUsernameSuccess() {
        theField = etUsername.text.toString()
        hideLoading()
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
        viewTransparent.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
        viewTransparent.visibility = View.GONE
    }

    private fun initData(userData: UserData) {
        val profileData = userData.profileData ?: ProfileData()

        theField = profileData.username

        tvTitle.text = getString(context, R.string.menu_profile_title)

        etUsername.setText(profileData.username)

        tvDate.text = profileData.userdate
        tvGames.text = getString(context, R.string.menu_profile_total_games, profileData.usergames)
        tvRating.text =
            getString(context, R.string.menu_profile_total_rating, profileData.userrating)

        if (profileData.userimage.isNotBlank()) {
            Glide.with(context)
                .load(profileData.userimage)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)) // Caching options
                .into(ivProfileImage)
        }

        val matches = userData.matchData

        if (matches.isNullOrEmpty()) {
            sectionMatch.visibility = View.GONE
        } else {
            sectionMatch.visibility = View.VISIBLE
            rvMatch.adapter = MatchAdapter(matches, context)
            rvMatch.layoutManager = LinearLayoutManager(context)
        }

        initComponent()
        disableChecklist()
    }


    private fun initComponent() {
        etUsername.setOnClickListener {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)

            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

            dialog.window?.attributes = layoutParams
        }

        ivCancel.setOnClickListener {
            dialog.dismiss()
        }

        ivEditUsername.setOnClickListener {
            setFocus()
        }

        ivDoneUsername.setOnClickListener {
            loseFocus(onDoneEditUsername)
        }

        btnChange.setOnClickListener {
            onChangePhoto?.invoke()
        }

        etUsername.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || actionId == EditorInfo.IME_ACTION_NEXT
            ) {
                val handler = Handler()
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window?.attributes)

                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                dialog.window?.attributes = layoutParams

                handler.postDelayed({
                    scrollView.scrollTo(0, 0)
                }, 410L)
                false
            } else {
                false
            }
        }
    }

    private fun setFocus() {
        etUsername.doBeforeTextChanged { text, _, _, _ ->
            validateUsername(text.toString())
        }

        etUsername.doOnTextChanged { text, _, _, _ ->
            validateUsername(text.toString())
        }

        etUsername.isEnabled = true
        etUsername.requestFocus()
    }

    private fun loseFocus(callback: ((String) -> Unit)? = null) {
        disableChecklist()

        callback?.invoke(etUsername.text.toString())

        etUsername.clearFocus()
        etUsername.isEnabled = false
    }

    private fun enableChecklist() {
        ivEditUsername.visibility = View.INVISIBLE
        ivDoneUsername.visibility = View.VISIBLE
    }

    private fun disableChecklist() {
        ivEditUsername.visibility = View.VISIBLE
        ivDoneUsername.visibility = View.GONE
    }

    private fun validateUsername(text: String) {
        if ((text) != theField && text.isNotEmpty()) enableChecklist()
        else disableChecklist()
    }
}
