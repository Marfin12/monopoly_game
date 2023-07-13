package com.example.experiments2

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.view.Display
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.experiments2.databinding.ActivityMainBinding
import com.example.experiments2.money.MoneyAdapter
import com.example.experiments2.money.MoneyData


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val moneyAdapter = MoneyAdapter(
        mutableListOf(
            MoneyData(1),
            MoneyData(2),
            MoneyData(5)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager
        val defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
        val defaultDisplayContext: Context = createDisplayContext(defaultDisplay)
        val displayMetrics =  defaultDisplayContext.resources.displayMetrics
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val dm = resources.displayMetrics

        val density = (dm.density * 160).toDouble()
        val x = Math.pow(dm.widthPixels / density, 2.0)
        val y = Math.pow(dm.heightPixels / density, 2.0)
        val screenInches = Math.sqrt(x + y)
        println("inches: {} $screenInches")

        println(height)
        println(width)

        initAdapterMoney()
    }

    private fun initAdapterMoney() {
        binding.mainProperties.rvMoneyList.adapter = moneyAdapter
        binding.mainProperties.rvMoneyList.layoutManager = LinearLayoutManager(this)
    }

    private fun initAdapterCard() {

    }
}