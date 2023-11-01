package com.example.experiments2.util

import android.view.View
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.experiments2.R


@Keep
object CityUtil {
    fun ImageView.setBuildingResource(image: Int) {
        val ivParent = this.parent as View

        if (ivParent.id == R.id.bottom_city) setConstraintBottomSide(this, image)
        if (ivParent.id == R.id.top_city) setConstraintTopSide(this, image)
        if (ivParent.id == R.id.left_city) setConstraintLeftSide(this, image)
        if (ivParent.id == R.id.right_city) setConstraintRightSide(this, image)

        this.setImageResource(image)
    }

    private fun setConstraintBottomSide(imageView: ImageView, image: Int) {
        when (image) {
            R.drawable.red_b -> {
                when (imageView.id) {
                    R.id.building_bottom_A ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_A_red
                            bottomToBottom = R.id.guideline_bottom_building_bottom_A_red
                            startToStart = R.id.guideline_start_building_bottom_A_red
                            endToEnd = R.id.guideline_end_building_bottom_A_red
                        }
                    R.id.building_bottom_B ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_BC_red
                            bottomToBottom = R.id.guideline_bottom_building_bottom_BC_red
                            startToStart = R.id.guideline_start_building_bottom_B_red
                            endToEnd = R.id.guideline_end_building_bottom_B_red
                        }
                    R.id.building_bottom_C ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_BC_red
                            bottomToBottom = R.id.guideline_bottom_building_bottom_BC_red
                            startToStart = R.id.guideline_start_building_bottom_C_red
                            endToEnd = R.id.guideline_end_building_bottom_C_red
                        }
                    R.id.building_bottom_D ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF_red
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF_red
                            startToStart = R.id.guideline_start_building_bottom_D_red
                            endToEnd = R.id.guideline_end_building_bottom_D_red
                        }
                    R.id.building_bottom_E ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF_red
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF_red
                            startToStart = R.id.guideline_start_building_bottom_A_red
                            endToEnd = R.id.guideline_end_building_bottom_A_red
                        }
                    R.id.building_bottom_F ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF_red
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF_red
                            startToStart = R.id.guideline_start_building_bottom_F_red
                            endToEnd = R.id.guideline_end_building_bottom_F_red
                        }
                }
            }
            R.drawable.yellow_b -> {
                when (imageView.id) {
                    R.id.building_bottom_A ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_A_yellow
                            bottomToBottom = R.id.guideline_bottom_building_bottom_A_yellow
                            startToStart = R.id.guideline_start_building_bottom_A_yellow
                            endToEnd = R.id.guideline_end_building_bottom_A_yellow
                        }
                    R.id.building_bottom_B ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_BC_yellow
                            bottomToBottom = R.id.guideline_bottom_building_bottom_BC_yellow
                            startToStart = R.id.guideline_start_building_bottom_B_yellow
                            endToEnd = R.id.guideline_end_building_bottom_B_yellow
                        }
                    R.id.building_bottom_C ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_BC_yellow
                            bottomToBottom = R.id.guideline_bottom_building_bottom_BC_yellow
                            startToStart = R.id.guideline_start_building_bottom_C_yellow
                            endToEnd = R.id.guideline_end_building_bottom_C_yellow
                        }
                    R.id.building_bottom_D ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF_yellow
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF_yellow
                            startToStart = R.id.guideline_start_building_bottom_D_yellow
                            endToEnd = R.id.guideline_end_building_bottom_D_yellow
                        }
                    R.id.building_bottom_E ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF_yellow
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF_yellow
                            startToStart = R.id.guideline_start_building_bottom_A_yellow
                            endToEnd = R.id.guideline_end_building_bottom_A_yellow
                        }
                    R.id.building_bottom_F ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF_yellow
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF_yellow
                            startToStart = R.id.guideline_start_building_bottom_F_yellow
                            endToEnd = R.id.guideline_end_building_bottom_F_yellow
                        }
                }
            }
            else -> {
                when (imageView.id) {
                    R.id.building_bottom_A ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_A
                            bottomToBottom = R.id.guideline_bottom_building_bottom_A
                            startToStart = R.id.guideline_start_building_bottom_A
                            endToEnd = R.id.guideline_end_building_bottom_A
                        }
                    R.id.building_bottom_B ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_BC
                            bottomToBottom = R.id.guideline_bottom_building_bottom_BC
                            startToStart = R.id.guideline_start_building_bottom_B
                            endToEnd = R.id.guideline_end_building_bottom_B
                        }
                    R.id.building_bottom_C ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_BC
                            bottomToBottom = R.id.guideline_bottom_building_bottom_BC
                            startToStart = R.id.guideline_start_building_bottom_C
                            endToEnd = R.id.guideline_end_building_bottom_C
                        }
                    R.id.building_bottom_D ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF
                            startToStart = R.id.guideline_start_building_bottom_D
                            endToEnd = R.id.guideline_end_building_bottom_D
                        }
                    R.id.building_bottom_E ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF
                            startToStart = R.id.guideline_start_building_bottom_A
                            endToEnd = R.id.guideline_end_building_bottom_A
                        }
                    R.id.building_bottom_F ->
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = R.id.guideline_top_building_bottom_DEF
                            bottomToBottom = R.id.guideline_bottom_building_bottom_DEF
                            startToStart = R.id.guideline_start_building_bottom_F
                            endToEnd = R.id.guideline_end_building_bottom_F
                        }
                }
            }
        }
    }

    private fun setConstraintTopSide(imageView: ImageView, image: Int) {
        when (image) {
            R.drawable.red_b -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_A_red
                        bottomToBottom = R.id.guideline_bottom_building_top_A_red
                        startToStart = R.id.guideline_start_building_top_A_red
                        endToEnd = R.id.guideline_end_building_top_A_red
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_BC_red
                        bottomToBottom = R.id.guideline_bottom_building_top_BC_red
                        startToStart = R.id.guideline_start_building_top_B_red
                        endToEnd = R.id.guideline_end_building_top_B_red
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_BC_red
                        bottomToBottom = R.id.guideline_bottom_building_top_BC_red
                        startToStart = R.id.guideline_start_building_top_C_red
                        endToEnd = R.id.guideline_end_building_top_C_red
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF_red
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF_red
                        startToStart = R.id.guideline_start_building_top_D_red
                        endToEnd = R.id.guideline_end_building_top_D_red
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF_red
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF_red
                        startToStart = R.id.guideline_start_building_top_A_red
                        endToEnd = R.id.guideline_end_building_top_A_red
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF_red
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF_red
                        startToStart = R.id.guideline_start_building_top_F_red
                        endToEnd = R.id.guideline_end_building_top_F_red
                    }
            }
            R.drawable.yellow_b -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_A_yellow
                        bottomToBottom = R.id.guideline_bottom_building_top_A_yellow
                        startToStart = R.id.guideline_start_building_top_A_yellow
                        endToEnd = R.id.guideline_end_building_top_A_yellow
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_BC_yellow
                        bottomToBottom = R.id.guideline_bottom_building_top_BC_yellow
                        startToStart = R.id.guideline_start_building_top_B_yellow
                        endToEnd = R.id.guideline_end_building_top_B_yellow
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_BC_yellow
                        bottomToBottom = R.id.guideline_bottom_building_top_BC_yellow
                        startToStart = R.id.guideline_start_building_top_C_yellow
                        endToEnd = R.id.guideline_end_building_top_C_yellow
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF_yellow
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF_yellow
                        startToStart = R.id.guideline_start_building_top_D_yellow
                        endToEnd = R.id.guideline_end_building_top_D_yellow
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF_yellow
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF_yellow
                        startToStart = R.id.guideline_start_building_top_A_yellow
                        endToEnd = R.id.guideline_end_building_top_A_yellow
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF_yellow
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF_yellow
                        startToStart = R.id.guideline_start_building_top_F_yellow
                        endToEnd = R.id.guideline_end_building_top_F_yellow
                    }
            }
            else -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_A
                        bottomToBottom = R.id.guideline_bottom_building_top_A
                        startToStart = R.id.guideline_start_building_top_A
                        endToEnd = R.id.guideline_end_building_top_A
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_BC
                        bottomToBottom = R.id.guideline_bottom_building_top_BC
                        startToStart = R.id.guideline_start_building_top_B
                        endToEnd = R.id.guideline_end_building_top_B
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_BC
                        bottomToBottom = R.id.guideline_bottom_building_top_BC
                        startToStart = R.id.guideline_start_building_top_C
                        endToEnd = R.id.guideline_end_building_top_C
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF
                        startToStart = R.id.guideline_start_building_top_D
                        endToEnd = R.id.guideline_end_building_top_D
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF
                        startToStart = R.id.guideline_start_building_top_A
                        endToEnd = R.id.guideline_end_building_top_A
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_top_DEF
                        bottomToBottom = R.id.guideline_bottom_building_top_DEF_yellow
                        startToStart = R.id.guideline_start_building_top_F_yellow
                        endToEnd = R.id.guideline_end_building_top_F_yellow
                    }
            }
        }
    }

    private fun setConstraintRightSide(imageView: ImageView, image: Int) {
        when (image) {
            R.drawable.red_b -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_A_red
                        bottomToBottom = R.id.guideline_bottom_building_right_A_red
                        startToStart = R.id.guideline_start_building_right_A_red
                        endToEnd = R.id.guideline_end_building_right_A_red
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_B_red
                        bottomToBottom = R.id.guideline_bottom_building_right_B_red
                        startToStart = R.id.guideline_start_building_right_BC_red
                        endToEnd = R.id.guideline_end_building_right_BC_red
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_C_red
                        bottomToBottom = R.id.guideline_bottom_building_right_C_red
                        startToStart = R.id.guideline_start_building_right_BC_red
                        endToEnd = R.id.guideline_end_building_right_BC_red
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_D_red
                        bottomToBottom = R.id.guideline_bottom_building_right_D_red
                        startToStart = R.id.guideline_start_building_right_DEF_red
                        endToEnd = R.id.guideline_end_building_right_DEF_red
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_A_red
                        bottomToBottom = R.id.guideline_bottom_building_right_A_red
                        startToStart = R.id.guideline_start_building_right_DEF_red
                        endToEnd = R.id.guideline_end_building_right_DEF_red
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_F_red
                        bottomToBottom = R.id.guideline_bottom_building_right_F_red
                        startToStart = R.id.guideline_start_building_right_DEF_red
                        endToEnd = R.id.guideline_end_building_right_DEF_red
                    }
            }
            R.drawable.yellow_b -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_A_yellow
                        bottomToBottom = R.id.guideline_bottom_building_right_A_yellow
                        startToStart = R.id.guideline_start_building_right_A_yellow
                        endToEnd = R.id.guideline_end_building_right_A_yellow
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_B_yellow
                        bottomToBottom = R.id.guideline_bottom_building_right_B_yellow
                        startToStart = R.id.guideline_start_building_right_BC_yellow
                        endToEnd = R.id.guideline_end_building_right_BC_yellow
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_C_yellow
                        bottomToBottom = R.id.guideline_bottom_building_right_C_yellow
                        startToStart = R.id.guideline_start_building_right_BC_yellow
                        endToEnd = R.id.guideline_end_building_right_BC_yellow
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_D_yellow
                        bottomToBottom = R.id.guideline_bottom_building_right_D_yellow
                        startToStart = R.id.guideline_start_building_right_DEF_yellow
                        endToEnd = R.id.guideline_end_building_right_DEF_yellow
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_A_yellow
                        bottomToBottom = R.id.guideline_bottom_building_right_A_yellow
                        startToStart = R.id.guideline_start_building_right_DEF_yellow
                        endToEnd = R.id.guideline_end_building_right_DEF_yellow
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_F_yellow
                        bottomToBottom = R.id.guideline_bottom_building_right_F_yellow
                        startToStart = R.id.guideline_start_building_right_DEF_yellow
                        endToEnd = R.id.guideline_end_building_right_DEF_yellow
                    }
            }
            else -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_A
                        bottomToBottom = R.id.guideline_bottom_building_right_A
                        startToStart = R.id.guideline_start_building_right_A
                        endToEnd = R.id.guideline_end_building_right_A
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_B
                        bottomToBottom = R.id.guideline_bottom_building_right_B
                        startToStart = R.id.guideline_start_building_right_BC
                        endToEnd = R.id.guideline_end_building_right_BC
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_C
                        bottomToBottom = R.id.guideline_bottom_building_right_C
                        startToStart = R.id.guideline_start_building_right_BC
                        endToEnd = R.id.guideline_end_building_right_BC
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_D
                        bottomToBottom = R.id.guideline_bottom_building_right_D
                        startToStart = R.id.guideline_start_building_right_DEF
                        endToEnd = R.id.guideline_end_building_right_DEF
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_A
                        bottomToBottom = R.id.guideline_bottom_building_right_A
                        startToStart = R.id.guideline_start_building_right_DEF
                        endToEnd = R.id.guideline_end_building_right_DEF
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_right_F
                        bottomToBottom = R.id.guideline_bottom_building_right_F
                        startToStart = R.id.guideline_start_building_right_DEF
                        endToEnd = R.id.guideline_end_building_right_DEF
                    }
            }
        }
    }

    private fun setConstraintLeftSide(imageView: ImageView, image: Int) {
        when (image) {
            R.drawable.red_b -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_A_red
                        bottomToBottom = R.id.guideline_bottom_building_left_A_red
                        startToStart = R.id.guideline_start_building_left_A_red
                        endToEnd = R.id.guideline_end_building_left_A_red
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_B_red
                        bottomToBottom = R.id.guideline_bottom_building_left_B_red
                        startToStart = R.id.guideline_start_building_left_BC_red
                        endToEnd = R.id.guideline_end_building_left_BC_red
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_C_red
                        bottomToBottom = R.id.guideline_bottom_building_left_C_red
                        startToStart = R.id.guideline_start_building_left_BC_red
                        endToEnd = R.id.guideline_end_building_left_BC_red
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_D_red
                        bottomToBottom = R.id.guideline_bottom_building_left_D_red
                        startToStart = R.id.guideline_start_building_left_DEF_red
                        endToEnd = R.id.guideline_end_building_left_DEF_red
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_A_red
                        bottomToBottom = R.id.guideline_bottom_building_left_A_red
                        startToStart = R.id.guideline_start_building_left_DEF_red
                        endToEnd = R.id.guideline_end_building_left_DEF_red
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_F_red
                        bottomToBottom = R.id.guideline_bottom_building_left_F_red
                        startToStart = R.id.guideline_start_building_left_DEF_red
                        endToEnd = R.id.guideline_end_building_left_DEF_red
                    }
            }
            R.drawable.yellow_b -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_A_yellow
                        bottomToBottom = R.id.guideline_bottom_building_left_A_yellow
                        startToStart = R.id.guideline_start_building_left_A_yellow
                        endToEnd = R.id.guideline_end_building_left_A_yellow
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_B_yellow
                        bottomToBottom = R.id.guideline_bottom_building_left_B_yellow
                        startToStart = R.id.guideline_start_building_left_BC_yellow
                        endToEnd = R.id.guideline_end_building_left_BC_yellow
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_C_yellow
                        bottomToBottom = R.id.guideline_bottom_building_left_C_yellow
                        startToStart = R.id.guideline_start_building_left_BC_yellow
                        endToEnd = R.id.guideline_end_building_left_BC_yellow
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_D_yellow
                        bottomToBottom = R.id.guideline_bottom_building_left_D_yellow
                        startToStart = R.id.guideline_start_building_left_DEF_yellow
                        endToEnd = R.id.guideline_end_building_left_DEF_yellow
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_A_yellow
                        bottomToBottom = R.id.guideline_bottom_building_left_A_yellow
                        startToStart = R.id.guideline_start_building_left_DEF_yellow
                        endToEnd = R.id.guideline_end_building_left_DEF_yellow
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_F_yellow
                        bottomToBottom = R.id.guideline_bottom_building_left_F_yellow
                        startToStart = R.id.guideline_start_building_left_DEF_yellow
                        endToEnd = R.id.guideline_end_building_left_DEF_yellow
                    }
            }
            else -> when (imageView.id) {
                R.id.building_bottom_A ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_A
                        bottomToBottom = R.id.guideline_bottom_building_left_A
                        startToStart = R.id.guideline_start_building_left_A
                        endToEnd = R.id.guideline_end_building_left_A
                    }
                R.id.building_bottom_B ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_B
                        bottomToBottom = R.id.guideline_bottom_building_left_B
                        startToStart = R.id.guideline_start_building_left_BC
                        endToEnd = R.id.guideline_end_building_left_BC
                    }
                R.id.building_bottom_C ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_C
                        bottomToBottom = R.id.guideline_bottom_building_left_C
                        startToStart = R.id.guideline_start_building_left_BC
                        endToEnd = R.id.guideline_end_building_left_BC
                    }
                R.id.building_bottom_D ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_D
                        bottomToBottom = R.id.guideline_bottom_building_left_D
                        startToStart = R.id.guideline_start_building_left_DEF
                        endToEnd = R.id.guideline_end_building_left_DEF
                    }
                R.id.building_bottom_E ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_A
                        bottomToBottom = R.id.guideline_bottom_building_left_A
                        startToStart = R.id.guideline_start_building_left_DEF
                        endToEnd = R.id.guideline_end_building_left_DEF
                    }
                R.id.building_bottom_F ->
                    imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToTop = R.id.guideline_top_building_left_F
                        bottomToBottom = R.id.guideline_bottom_building_left_F
                        startToStart = R.id.guideline_start_building_left_DEF
                        endToEnd = R.id.guideline_end_building_left_DEF
                    }
            }
        }
    }

}
