package com.example.experiments2.main

import android.view.View
import com.example.experiments2.databinding.ActivityMainBinding


interface MainVisibility {
    var isShowRv: Boolean
    var visibilityBinding: ActivityMainBinding

    fun showMainProperties() {
        visibilityBinding.gameOverlay.mainProperties.root.visibility = View.VISIBLE
        visibilityBinding.gameOverlay.payingProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.moneyProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.profileProperties.root.visibility = View.GONE
    }

    fun showPayingProperties() {
        visibilityBinding.gameOverlay.mainProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.payingProperties.root.visibility = View.VISIBLE
        visibilityBinding.gameOverlay.moneyProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.profileProperties.root.visibility = View.GONE
    }

    fun showMoneyProperties() {
        visibilityBinding.gameOverlay.mainProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.payingProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.moneyProperties.root.visibility = View.VISIBLE
        visibilityBinding.gameOverlay.profileProperties.root.visibility = View.GONE
    }

    fun showProfileProperties() {
        visibilityBinding.gameOverlay.mainProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.payingProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.moneyProperties.root.visibility = View.GONE
        visibilityBinding.gameOverlay.profileProperties.root.visibility = View.VISIBLE
    }

    fun hideAllLoadingSide() {
        visibilityBinding.gameBackground.ivLoadingBottomSide.visibility = View.GONE
        visibilityBinding.gameBackground.ivLoadingRightSide.visibility = View.GONE
        visibilityBinding.gameBackground.ivLoadingTopSide.visibility = View.GONE
        visibilityBinding.gameBackground.ivLoadingLeftSide.visibility = View.GONE
    }

    fun showPlayerCard() {
        visibilityBinding.gameOverlay.playerCardList.root.visibility = View.VISIBLE
        isShowRv = true
    }

    fun hidePlayerCard() {
        visibilityBinding.gameOverlay.playerCardList.root.visibility = View.GONE
        isShowRv = false
    }
}