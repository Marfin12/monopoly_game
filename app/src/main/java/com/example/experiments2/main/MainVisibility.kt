package com.example.experiments2.main

import android.view.View
import com.example.experiments2.databinding.ActivityMainBinding


interface MainVisibility {
    var isShowRv: Boolean
    var visibilityBinding: ActivityMainBinding

    fun showMainProperties() {
        visibilityBinding.gameOverlay.propertiesMain.root.visibility = View.VISIBLE
        visibilityBinding.gameOverlay.propertiesPaying.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesMoney.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesProfile.root.visibility = View.GONE
    }

    fun showPayingProperties() {
        visibilityBinding.gameOverlay.propertiesMain.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesPaying.root.visibility = View.VISIBLE
        visibilityBinding.gameOverlay.propertiesMoney.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesProfile.root.visibility = View.GONE
    }

    fun showMoneyProperties() {
        visibilityBinding.gameOverlay.propertiesMain.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesPaying.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesMoney.root.visibility = View.VISIBLE
        visibilityBinding.gameOverlay.propertiesProfile.root.visibility = View.GONE
    }

    fun showProfileProperties() {
        visibilityBinding.gameOverlay.propertiesMain.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesPaying.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesMoney.root.visibility = View.GONE
        visibilityBinding.gameOverlay.propertiesProfile.root.visibility = View.VISIBLE
    }

    fun hideAllLoadingSide() {
        visibilityBinding.gameBackground.ivLoadingBottomSide.visibility = View.GONE
        visibilityBinding.gameBackground.ivLoadingRightSide.visibility = View.GONE
        visibilityBinding.gameBackground.ivLoadingTopSide.visibility = View.GONE
        visibilityBinding.gameBackground.ivLoadingLeftSide.visibility = View.GONE
    }

    fun showPlayerCard() {
        visibilityBinding.gameOverlay.propertiesMyCard.root.visibility = View.VISIBLE
        isShowRv = true
    }

    fun hidePlayerCard() {
        visibilityBinding.gameOverlay.propertiesMyCard.root.visibility = View.GONE
        isShowRv = false
    }
}