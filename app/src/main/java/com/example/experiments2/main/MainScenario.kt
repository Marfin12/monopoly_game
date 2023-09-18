package com.example.experiments2.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.media.MediaPlayer
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.animation.doOnEnd
import com.example.experiments2.R
import com.example.experiments2.Util.generateAssetCardType
import com.example.experiments2.Util.generateNonAssetCardType
import com.example.experiments2.Util.locationX
import com.example.experiments2.Util.locationY
import com.example.experiments2.Util.playGif
import com.example.experiments2.Util.setBuildingResource
import com.example.experiments2.card.CardData
import com.example.experiments2.databinding.ActivityMainBinding


interface MainScenario {
    var scenarioBinding: ActivityMainBinding
    val listImageView: MutableList<ImageView>

    var loadingAnim: ObjectAnimator?
    var playerLoadingTurn: Int

    var isCardClicked: Boolean
    var pendingLoading: ImageView?

    fun setTrianglePlayerExpiry() {
        val ivLoading = listImageView[playerLoadingTurn]

        ivLoading.visibility = View.VISIBLE
        scenarioBinding.motionSide.transitionToState(R.id.start)
        loadingAnim = ObjectAnimator.ofFloat(ivLoading, View.ALPHA, 0F, 1F).apply {
            duration = 500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
        loadingAnim?.start()

        scenarioBinding.motionSide.transitionToState(R.id.end, 15000)
        scenarioBinding.motionSide.cancelPendingInputEvents()

        scenarioBinding.motionSide.setTransitionListener(object : MotionLayout.TransitionListener {
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

        scenarioBinding.motionSide.progress = 0.0F
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

//        if (ivLoading == scenarioBinding.ivLoadingBottomSide)
//            scenarioBinding.gameMessage.show(
//                "Expiry Message",
//                "Expired!!",
//                "Ok, I understand"
//            )
    }

    fun onCardItemClick(cardData: CardData, imageView: ImageView, context: Context) {
        isCardClicked = true
        scenarioBinding.animatedCard.root.visibility = View.VISIBLE
        scenarioBinding.animatedCard.rootAssetCard.ivBuildingCard.visibility = View.VISIBLE
        scenarioBinding.playerCardList.root.visibility = View.GONE

        playGif(context, R.raw.sparkle, scenarioBinding.animatedCard.ivCardEffect, 1)
        MediaPlayer.create(context, R.raw.whoosh).start()

        scenarioBinding.animatedCard.root.x = imageView.locationX()
        scenarioBinding.animatedCard.root.y = imageView.locationY()

        drawCardDropped(cardData, context)
        animAssetCardDropped(context, scenarioBinding.incBottomCity.buildingBottomC)
    }

    fun onEnemyChooseCard(cardData: CardData, context: Context) {
        val testLocation = scenarioBinding.incRightCity.buildingRightE

        testLocation.post {
//        isCardClicked = true
            scenarioBinding.animatedCard.root.visibility = View.VISIBLE
            scenarioBinding.animatedCard.rootAssetCard.ivBuildingCard.visibility = View.VISIBLE
//        scenarioBinding.playerCardList.root.visibility = View.GONE

            playGif(context, R.raw.sparkle, scenarioBinding.animatedCard.ivCardEffect, 1)
            MediaPlayer.create(context, R.raw.whoosh).start()
//
            println("locationX: " + testLocation.locationX())
            println("locationY: " + testLocation.locationY())
            scenarioBinding.animatedCard.root.x = testLocation.locationX()
            scenarioBinding.animatedCard.root.y = testLocation.locationY()

            drawCardDropped(cardData, context)
            animAssetCardDropped(context, scenarioBinding.incRightCity.buildingRightA)
        }
    }

    fun drawCardDropped(cardData: CardData, context: Context) {
        if (cardData.assetPriceList != null) {
            generateAssetCardType(
                cardData,
                scenarioBinding.animatedCard.ivCard,
                scenarioBinding.animatedCard.rootAssetCard.root,
                scenarioBinding.animatedCard.rootMoneyCard.root,
                context
            )
        } else {
            generateNonAssetCardType(
                cardData,
                scenarioBinding.animatedCard.ivCard,
                scenarioBinding.animatedCard.rootAssetCard.root,
                scenarioBinding.animatedCard.rootMoneyCard.root
            )
        }
    }

    fun animAssetCardDropped(context: Context, buildingPlace: ImageView) {
        val cardStartX = scenarioBinding.animatedCard.root.x
        val cardStartY = scenarioBinding.animatedCard.root.y
        val cardDroppingAssetX = buildingPlace.x + 195
        val cardDroppingAssetY = buildingPlace.y - 54

        val bounceUpMoveScene = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    scenarioBinding.animatedCard.root, View.X, cardStartX, cardStartX - 200
                ).apply { duration = 1000 }
            )
        }
        val droppingAssetMoveScene = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(
                    scenarioBinding.animatedCard.root,
                    View.X,
                    cardStartX - 200,
                    cardDroppingAssetX
                ).apply { duration = 500 },
                ObjectAnimator.ofFloat(
                    scenarioBinding.animatedCard.root, View.Y, cardStartY, cardDroppingAssetY
                ).apply { duration = 500 },
            )
        }

        val bounceUpScaleScene = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(
                    scenarioBinding.animatedCard.root,
                    View.SCALE_X,
                    1F,
                    1.5F
                )
                    .apply {
                        duration = 750
                    },
                ObjectAnimator.ofFloat(
                    scenarioBinding.animatedCard.root,
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
                    scenarioBinding.animatedCard.root,
                    View.SCALE_X,
                    1.5F,
                    0.5F
                )
                    .apply {
                        duration = 500
                    },
                ObjectAnimator.ofFloat(
                    scenarioBinding.animatedCard.root,
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
                scenarioBinding.animatedCard.rootCard,
                View.ALPHA,
                1F,
                0F
            ).apply { duration = 800 }.start()

            playGif(context, R.raw.building_effect, scenarioBinding.animatedCard.ivCardEffect, 1)
            playGif(context, R.raw.testonly, buildingPlace, 1, onAnimationEnd = {
                isCardClicked = false

                if (pendingLoading != null) nextPlayer(pendingLoading!!)

                pendingLoading = null
            }
            )
        }
    }
}
