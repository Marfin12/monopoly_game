package com.example.experiments2.main

import android.view.View
import com.example.experiments2.databinding.ActivityMainBinding

interface MainVisibility {
    var isShowRv: Boolean
    var visibilityBinding: ActivityMainBinding

    fun showMainProperties() {
        visibilityBinding.mainProperties.root.visibility = View.VISIBLE
        visibilityBinding.payingProperties.root.visibility = View.GONE
        visibilityBinding.moneyProperties.root.visibility = View.GONE
        visibilityBinding.profileProperties.root.visibility = View.GONE
    }

    fun showPayingProperties() {
        visibilityBinding.mainProperties.root.visibility = View.GONE
        visibilityBinding.payingProperties.root.visibility = View.VISIBLE
        visibilityBinding.moneyProperties.root.visibility = View.GONE
        visibilityBinding.profileProperties.root.visibility = View.GONE
    }

    fun showMoneyProperties() {
        visibilityBinding.mainProperties.root.visibility = View.GONE
        visibilityBinding.payingProperties.root.visibility = View.GONE
        visibilityBinding.moneyProperties.root.visibility = View.VISIBLE
        visibilityBinding.profileProperties.root.visibility = View.GONE
    }

    fun showProfileProperties() {
        visibilityBinding.mainProperties.root.visibility = View.GONE
        visibilityBinding.payingProperties.root.visibility = View.GONE
        visibilityBinding.moneyProperties.root.visibility = View.GONE
        visibilityBinding.profileProperties.root.visibility = View.VISIBLE
    }

    fun hideAllLoadingSide() {
        visibilityBinding.ivLoadingBottomSide.visibility = View.GONE
        visibilityBinding.ivLoadingRightSide.visibility = View.GONE
        visibilityBinding.ivLoadingTopSide.visibility = View.GONE
        visibilityBinding.ivLoadingLeftSide.visibility = View.GONE
    }

    fun showPlayerCard() {
        visibilityBinding.playerCardList.root.visibility = View.VISIBLE
        isShowRv = true
    }

    fun hidePlayerCard() {
        visibilityBinding.playerCardList.root.visibility = View.GONE
        isShowRv = false
    }
}