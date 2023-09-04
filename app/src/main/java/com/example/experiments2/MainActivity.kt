package com.example.experiments2

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.display.DisplayManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.experiments2.card.CardAdapter
import com.example.experiments2.databinding.ActivityMainBinding
import com.example.experiments2.money.MoneyAdapter
import com.example.experiments2.money.MoneyData


class MainActivity : AppCompatActivity(), MainVisibility, MainScenario {

    private lateinit var binding: ActivityMainBinding

    private val moneyAdapter = MoneyAdapter(
        mutableListOf(
            MoneyData(1),
            MoneyData(2),
            MoneyData(5)
        )
    )

    private val playerCardAdapter = CardAdapter(Util.getDummyPlayerCard(6), this@MainActivity)

    override var scenarioBinding: ActivityMainBinding
        get() = binding
        set(value) {}
    override var playerLoadingTurn = 0
    override val listImageView = mutableListOf<ImageView>()
    override var loadingAnim: ObjectAnimator? = null
    override var pendingLoading: ImageView? = null
    override var isCardClicked = false

    override var visibilityBinding: ActivityMainBinding
        get() = binding
        set(value) {}
    override var isShowRv = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scenarioBinding = binding
        visibilityBinding = binding

        supportActionBar?.hide()
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager
        val defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
        val defaultDisplayContext: Context = createDisplayContext(defaultDisplay)
        val displayMetrics = defaultDisplayContext.resources.displayMetrics
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

        initAnim()
        initComponent()
        initAdapterMoney()
        initAdapterCard()
    }

    private fun initAnim() {
        listImageView.add(binding.ivLoadingBottomSide)
        listImageView.add(binding.ivLoadingRightSide)
        listImageView.add(binding.ivLoadingTopSide)
        listImageView.add(binding.ivLoadingLeftSide)

        binding.ivLoadingBottomSide.visibility = View.GONE
        binding.ivLoadingRightSide.visibility = View.GONE
        binding.ivLoadingTopSide.visibility = View.GONE
        binding.ivLoadingLeftSide.visibility = View.GONE

        setTrianglePlayerExpiry()
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
        binding.mainProperties.gameBtnSkip.setOnClickListener {
            if (playerLoadingTurn == 0) finishPlayerTurn(listImageView[playerLoadingTurn])
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
        playerCardAdapter.onAssetItemClick = { cardData, obj ->
            onCardItemClick(cardData, obj, this@MainActivity)
        }
    }
}