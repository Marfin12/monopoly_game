package com.example.experiments2.main

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.display.DisplayManager
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
import com.example.experiments2.util.CardUtil


class MainActivity : AppCompatActivity(), MainVisibility, MainScenario {

    private lateinit var binding: ActivityMainBinding

    private val moneyAdapter = MoneyAdapter(
        mutableListOf(
            MoneyData(1),
            MoneyData(2),
            MoneyData(5)
        )
    )

    private val playerCardAdapter = CardAdapter(CardUtil.getDummyPlayerCard(6), this@MainActivity)

    override var scenarioBinding: ActivityMainBinding
        get() = binding
        set(_) {}
    override var playerLoadingTurn = 0
    override val listImageView = mutableListOf<ImageView>()
    override var loadingAnim: ObjectAnimator? = null
    override var pendingLoading: ImageView? = null
    override var isCardClicked = false

    override var visibilityBinding: ActivityMainBinding
        get() = binding
        set(_) {}
    override var isShowRv = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scenarioBinding = binding
        visibilityBinding = binding

        supportActionBar?.hide()

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

//        scenarioBinding.gameContent.bottomCity.buildingBottomA.setBuildingResource(
//            R.drawable.yellow_b
//        )
    }

    private fun initAnim() {
        listImageView.add(binding.gameBackground.ivLoadingBottomSide)
        listImageView.add(binding.gameBackground.ivLoadingRightSide)
        listImageView.add(binding.gameBackground.ivLoadingTopSide)
        listImageView.add(binding.gameBackground.ivLoadingLeftSide)

        hideAllLoadingSide()
        setTrianglePlayerExpiry()
    }

    private fun initComponent() {
        with(binding.gameOverlay) {
            propertiesMain.ivHolderInfo.setOnClickListener {
                showProfileProperties()
            }
            propertiesProfile.btnGameBack.setOnClickListener {
                showMainProperties()
            }
            propertiesProfile.ivHolderMoneyInfo.setOnClickListener {
                showMoneyProperties()
            }
            propertiesMoney.btnGameBack.setOnClickListener {
                showProfileProperties()
            }
            propertiesMain.ivHolderCard.setOnClickListener {
                if (isShowRv) hidePlayerCard() else showPlayerCard()
            }
            propertiesMyCard.root.setOnClickListener {
                hidePlayerCard()
            }
            propertiesMain.btnGameSkip.setOnClickListener {
                if (playerLoadingTurn == 0) finishPlayerTurn(listImageView[playerLoadingTurn])
            }
            binding.gameContent.bottomCity.root.setOnClickListener {
                onEnemyChooseCard(CardUtil.getDummyPlayerCard(1)[0], this@MainActivity)
            }
        }
    }

    private fun initAdapterMoney() {
        binding.gameOverlay.propertiesMoney.rvMoneyList.adapter = moneyAdapter
        binding.gameOverlay.propertiesMoney.rvMoneyList.layoutManager = LinearLayoutManager(this)
    }

    private fun initAdapterCard() {
        binding.gameOverlay.propertiesMyCard.rvListCard.adapter = playerCardAdapter
        binding.gameOverlay.propertiesMyCard.rvListCard.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        playerCardAdapter.onAssetItemClick = { cardData, itemAsset ->
            onCardItemClick(cardData, itemAsset, this@MainActivity)
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
}