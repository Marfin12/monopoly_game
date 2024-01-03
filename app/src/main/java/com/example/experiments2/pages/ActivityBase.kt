package com.example.experiments2.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


@SuppressLint("CustomSplashScreen")
abstract class ActivityBase<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding : T

    abstract fun inflateBinding(layoutInflater: LayoutInflater): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflateBinding(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
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

    fun showToastShort(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun showToastLong(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}