package com.example.experiments2

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.experiments2.card.CardAdapter
import com.example.experiments2.databinding.ActivityMainBinding
import com.example.experiments2.money.MoneyAdapter
import com.example.experiments2.money.MoneyData


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

    private val playerCardAdapter = CardAdapter(Util.getDummyPlayerCard(6), this@MainActivity)

    private var playerLoadingTurn = 0
    private val listImageView = mutableListOf<ImageView>()
    private var objAnim: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun setTrianglePlayerExpiry() {
        val ivLoading = listImageView[playerLoadingTurn]

        ivLoading.visibility = View.VISIBLE
        binding.motionSide.transitionToState(R.id.start)
        objAnim = ObjectAnimator.ofFloat(ivLoading, View.ALPHA, 0F, 1F).apply {
            duration = 500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
        objAnim?.start()
        binding.motionSide.transitionToState(R.id.end, 15000)

        binding.motionSide.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (currentId == R.id.end) {
                    ivLoading.visibility = View.GONE

                    objAnim?.cancel()
                    nextLoadingPlayerExpiry()
                    setTrianglePlayerExpiry()

                    if (ivLoading == binding.ivLoadingBottomSide) binding.gameMessage.show(
                        "Expiry Message",
                        "Expired!!",
                        "Ok, I understand"
                    )
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }

    private fun nextLoadingPlayerExpiry() {
        if (playerLoadingTurn < 3) playerLoadingTurn++
        else playerLoadingTurn = 0

        binding.motionSide.progress = 0.0F
    }

    private fun finishPlayerTurn(ivLoading: ImageView) {
        ivLoading.visibility = View.GONE

        objAnim?.cancel()
        nextLoadingPlayerExpiry()
        setTrianglePlayerExpiry()
    }
}