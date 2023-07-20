package com.example.experiments2

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.view.Display
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.experiments2.card.CardAdapter
import com.example.experiments2.card.CardData
import com.example.experiments2.card.CardEnum
import com.example.experiments2.databinding.ActivityMainBinding
import com.example.experiments2.money.MoneyAdapter
import com.example.experiments2.money.MoneyData
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isShowRv = false

    private val moneyAdapter = MoneyAdapter(
        mutableListOf(
            MoneyData(1),
            MoneyData(2),
            MoneyData(5)
        )
    )

    private val playerCardAdapter = CardAdapter(Util.getDummyPlayerCard(7), this@MainActivity)

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

        initComponent()
        initAdapterMoney()
        initAdapterCard()

//        binding.motionBottomSide.transitionToState(R.id.end)
//        binding.motionBottomSide.transitionToState(R.id.start)
    }

    private fun initComponent() {
        binding.mainProperties.holderInfo.setOnClickListener {
            showProfileProperties()
        }
        binding.profileProperties.holderMoneyInfo.setOnClickListener {
            showMoneyProperties()
        }
        binding.profileProperties.gameBtnBack.setOnClickListener {
            showMainProperties()
        }
        binding.moneyProperties.gameBtnBack.setOnClickListener {
            showProfileProperties()
        }
        binding.mainProperties.holderCard.setOnClickListener {
            if (isShowRv) hidePlayerCard() else showPlayerCard()
        }
        binding.playerCardList.root.setOnClickListener {
            hidePlayerCard()
        }
    }


    private fun initAdapterMoney() {
        binding.moneyProperties.rvMoneyList.adapter = moneyAdapter
        binding.moneyProperties.rvMoneyList.layoutManager = LinearLayoutManager(this)
    }

    private fun initAdapterCard() {
        binding.playerCardList.rvListCard.adapter = playerCardAdapter
        binding.playerCardList.rvListCard.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun showMainProperties() {
        binding.mainProperties.root.visibility = View.VISIBLE
        binding.payingProperties.root.visibility = View.GONE
        binding.moneyProperties.root.visibility = View.GONE
        binding.profileProperties.root.visibility = View.GONE
    }

    private fun showPayingProperties() {
        binding.mainProperties.root.visibility = View.GONE
        binding.payingProperties.root.visibility = View.VISIBLE
        binding.moneyProperties.root.visibility = View.GONE
        binding.profileProperties.root.visibility = View.GONE
    }

    private fun showMoneyProperties() {
        binding.mainProperties.root.visibility = View.GONE
        binding.payingProperties.root.visibility = View.GONE
        binding.moneyProperties.root.visibility = View.VISIBLE
        binding.profileProperties.root.visibility = View.GONE
    }

    private fun showProfileProperties() {
        binding.mainProperties.root.visibility = View.GONE
        binding.payingProperties.root.visibility = View.GONE
        binding.moneyProperties.root.visibility = View.GONE
        binding.profileProperties.root.visibility = View.VISIBLE
    }

    private fun showPlayerCard() {
        binding.playerCardList.root.visibility = View.VISIBLE
        isShowRv = true
    }

    private fun hidePlayerCard() {
        binding.playerCardList.root.visibility = View.GONE
        isShowRv = false
    }
}