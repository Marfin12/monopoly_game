package com.example.experiments2.pages.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.experiments2.component.dialog.GameProfile
import com.example.experiments2.component.dialog.GameRoomCard
import com.example.experiments2.component.dialog.GameSettings
import com.example.experiments2.databinding.ActivityMenuBinding
import com.example.experiments2.pages.main.MainActivity


class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MenuActivity::class.java)
            activity.startActivity(intent)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager) { title ->
            onShowTransfloat()
            GameRoomCard.newInstance(this).apply {
                onDismissListener = { onDismissTransfloat() }
                show(title, onPositiveButtonClick = {
                    MainActivity.launch(this@MenuActivity)
                })
            }
        }

        binding.ivMore.setOnClickListener {
            onShowTransfloat()
            GameSettings.newInstance(this).apply {
                onDismissListener = { onDismissTransfloat() }
                show("ok", "reset")
            }
        }

        binding.ivProfile.setOnClickListener {
            onShowTransfloat()
            GameProfile.newInstance(this).apply {
                onDismissListener = { onDismissTransfloat() }
                show()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    private fun onShowTransfloat() {
        binding.tabLayout.isEnabled = false
    }

    private fun onDismissTransfloat() {
        binding.tabLayout.isEnabled = true
    }
}