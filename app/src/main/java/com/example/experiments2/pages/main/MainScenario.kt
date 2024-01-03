package com.example.experiments2.pages.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.media.MediaPlayer
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.animation.doOnEnd
import com.example.experiments2.R
import com.example.experiments2.card.CardData
import com.example.experiments2.component.dialog.GameMessage
import com.example.experiments2.databinding.ActivityMainBinding
import com.example.experiments2.util.CardUtil.generateAssetCardType
import com.example.experiments2.util.CardUtil.generateNonAssetCardType
import com.example.experiments2.util.Util.itemAdapterX
import com.example.experiments2.util.Util.itemAdapterY
import com.example.experiments2.util.Util.playGif


interface MainScenario {
    var context: Context

    var scenarioBinding: ActivityMainBinding
    val listImageView: MutableList<ImageView>

    var loadingAnim: ObjectAnimator?
    var playerLoadingTurn: Int

    var isCardClicked: Boolean
    var pendingLoading: ImageView?

    fun setTrianglePlayerExpiry() {
        val ivLoading = listImageView[playerLoadingTurn]

        ivLoading.visibility = View.VISIBLE
        scenarioBinding.gameBackground.root.transitionToState(R.id.start)
        loadingAnim = ObjectAnimator.ofFloat(ivLoading, View.ALPHA, 0F, 1F).apply {
            duration = 500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
        loadingAnim?.start()

        scenarioBinding.gameBackground.root.transitionToState(R.id.end, 15000)
        scenarioBinding.gameBackground.root.cancelPendingInputEvents()

        scenarioBinding.gameBackground.root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (currentId == R.id.end && !isCardClicked) nextPlayer(ivLoading)
                if (isCardClicked) pendingLoading = ivLoading
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {}
        })
    }

    fun nextLoadingPlayerExpiry() {
        if (playerLoadingTurn < 3) playerLoadingTurn++
        else playerLoadingTurn = 0

        scenarioBinding.gameBackground.root.progress = 0.0F
    }

    fun finishPlayerTurn(ivLoading: ImageView) {
        ivLoading.visibility = View.GONE

        loadingAnim?.cancel()
        nextLoadingPlayerExpiry()
        setTrianglePlayerExpiry()
    }

    fun nextPlayer(ivLoading: ImageView) {
        ivLoading.visibility = View.GONE

        loadingAnim?.cancel()
        nextLoadingPlayerExpiry()
        setTrianglePlayerExpiry()

        if (ivLoading == scenarioBinding.gameBackground.ivLoadingBottomSide)
            GameMessage.newInstance(context).show(
                "Expiry Message",
                "Expired!!",
                "Ok, I understand"
            )
    }

    fun onCardItemClick(cardData: CardData, itemAsset: ImageView) {
        isCardClicked = true
        scenarioBinding.gameContent.animatedCard.root.visibility = View.VISIBLE
        scenarioBinding.gameContent.animatedCard.rootAssetCard.ivBuildingCard.visibility = View.VISIBLE
        scenarioBinding.gameOverlay.propertiesMyCard.root.visibility = View.GONE

        playGif(context, R.raw.sparkle, scenarioBinding.gameContent.animatedCard.ivCardEffect, 1)
        MediaPlayer.create(context, R.raw.whoosh).start()

        scenarioBinding.gameContent.animatedCard.root.x = itemAsset.itemAdapterX()
        scenarioBinding.gameContent.animatedCard.root.y = itemAsset.itemAdapterY()

        drawCardDropped(cardData, context)
        animAssetCardDropped(context, scenarioBinding.gameContent.rightCity.buildingRightD)
    }

    fun onEnemyChooseCard(cardData: CardData) {
        val testLocation = scenarioBinding.gameContent.rightCity.buildingRightE

        testLocation.post {
//        isCardClicked = true
            scenarioBinding.gameContent.animatedCard.root.visibility = View.VISIBLE
            scenarioBinding.gameContent.animatedCard.rootAssetCard.ivBuildingCard.visibility = View.VISIBLE
//        scenarioBinding.playerCardList.root.visibility = View.GONE

            playGif(context, R.raw.sparkle, scenarioBinding.gameContent.animatedCard.ivCardEffect, 1)
            MediaPlayer.create(context, R.raw.whoosh).start()
            scenarioBinding.gameContent.animatedCard.root.x = testLocation.itemAdapterX()
            scenarioBinding.gameContent.animatedCard.root.y = testLocation.itemAdapterY()

            drawCardDropped(cardData, context)
            animAssetCardDropped(context, scenarioBinding.gameContent.rightCity.buildingRightA)
        }
    }

    fun drawCardDropped(cardData: CardData, context: Context) {
        if (cardData.assetPriceList != null) {
            generateAssetCardType(
                cardData,
                scenarioBinding.gameContent.animatedCard.ivCardAction,
                scenarioBinding.gameContent.animatedCard.rootAssetCard.root,
                scenarioBinding.gameContent.animatedCard.rootMoneyCard.root,
                context
            )
        } else {
            generateNonAssetCardType(
                cardData,
                scenarioBinding.gameContent.animatedCard.ivCardAction,
                scenarioBinding.gameContent.animatedCard.rootAssetCard.root,
                scenarioBinding.gameContent.animatedCard.rootMoneyCard.root
            )
        }
    }

    fun animAssetCardDropped(context: Context, buildingPlace: ImageView) {
        val cardStartX = scenarioBinding.gameContent.animatedCard.root.x
        val cardStartY = scenarioBinding.gameContent.animatedCard.root.y
        val cardDroppingAssetX = buildingPlace.x
        val cardDroppingAssetY = buildingPlace.y

        val bounceUpMoveScene = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    scenarioBinding.gameContent.animatedCard.root, View.X, cardStartX, cardStartX - 200
                ).apply { duration = 1000 }
            )
        }
        val droppingAssetMoveScene = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(
                    scenarioBinding.gameContent.animatedCard.root,
                    View.X,
                    cardStartX - 200,
                    cardDroppingAssetX
                ).apply { duration = 500 },
                ObjectAnimator.ofFloat(
                    scenarioBinding.gameContent.animatedCard.root, View.Y, cardStartY, cardDroppingAssetY
                ).apply { duration = 500 },
            )
        }

        val bounceUpScaleScene = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(
                    scenarioBinding.gameContent.animatedCard.root,
                    View.SCALE_X,
                    1F,
                    1.5F
                )
                    .apply {
                        duration = 750
                    },
                ObjectAnimator.ofFloat(
                    scenarioBinding.gameContent.animatedCard.root,
                    View.SCALE_Y,
                    1F,
                    1.5F
                )
                    .apply {
                        duration = 750
                    },
            )
        }
        val droppingAssetScaleScene = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(
                    scenarioBinding.gameContent.animatedCard.root,
                    View.SCALE_X,
                    1.5F,
                    0.5F
                )
                    .apply {
                        duration = 500
                    },
                ObjectAnimator.ofFloat(
                    scenarioBinding.gameContent.animatedCard.root,
                    View.SCALE_Y,
                    1.5F,
                    0.5F
                )
                    .apply {
                        duration = 500
                    }
            )
            MediaPlayer.create(context, R.raw.card_dropped).start()
        }

        val animatorSet = AnimatorSet()

        animatorSet.playTogether(
            AnimatorSet().apply {
                playSequentially(bounceUpMoveScene, droppingAssetMoveScene)
                playSequentially(bounceUpScaleScene, droppingAssetScaleScene)
            }
        )

        animatorSet.start()
        animatorSet.doOnEnd {
//            buildingPlace.setBuildingResource(R.drawable.blue_b)

            ObjectAnimator.ofFloat(
                scenarioBinding.gameContent.animatedCard.rootCard,
                View.ALPHA,
                1F,
                0F
            ).apply { duration = 800 }.start()

            playGif(context, R.raw.building_effect, scenarioBinding.gameContent.animatedCard.ivCardEffect, 1)
            playGif(context, R.raw.testonly, buildingPlace, 1, onAnimationEnd = {
                isCardClicked = false

                if (pendingLoading != null) nextPlayer(pendingLoading!!)

                pendingLoading = null
            }
            )
        }
    }
}
