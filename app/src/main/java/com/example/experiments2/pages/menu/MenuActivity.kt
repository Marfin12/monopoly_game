package com.example.experiments2.pages.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.experiments2.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MenuActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager) { title ->
            binding.gameRoomCard.show(title)
        }

        binding.ivMore.setOnClickListener {
            binding.gameSettings.show("ok", "reset")
        }

        binding.ivProfile.setOnClickListener {
            binding.gameProfile.show()
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
}