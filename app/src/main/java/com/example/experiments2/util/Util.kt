package com.example.experiments2.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.provider.Settings
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.experiments2.MyApplication.Companion.appResources


@Keep
object Util {
    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun playGif(
        context: Context,
        gifResource: Int,
        targetView: ImageView,
        totalLoop: Int = 0,
        onAnimationEnd: (() -> Unit)? = null
    ) {
        Glide.with(context)
            .asGif()
            .transition(withCrossFade())
            .load(gifResource)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (totalLoop > 0) resource?.setLoopCount(totalLoop)
                    resource?.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable) {
                            onAnimationEnd?.invoke()

                            super.onAnimationEnd(drawable)
                        }
                    })
                    return false
                }
            })
            .into(targetView)
    }

    fun View.itemAdapterX(): Float = arrayLocationObj(this)[0].toFloat()
    fun View.itemAdapterY(): Float = arrayLocationObj(this)[1].toFloat()
    fun ConstraintLayout.addViewByLayoutId(layoutId: Int, centerTo: View? = null) {
        val childView = LayoutInflater.from(context).inflate(layoutId, null) as ConstraintLayout

        this.addView(childView)

        val whichView = centerTo ?: this

        if (centerTo != null) {
            this.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToTop = whichView.id
                bottomToBottom = whichView.id
                startToStart = whichView.id
                endToEnd = whichView.id
            }
        } else {
            childView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToTop = whichView.id
                bottomToBottom = whichView.id
                startToStart = whichView.id
                endToEnd = whichView.id
            }
        }
    }
    fun Int.toDp() : Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            appResources.displayMetrics
        ).toInt()
    }

    private fun arrayLocationObj(obj: View): IntArray {
        val originalPos = IntArray(2)
        obj.getLocationInWindow(originalPos)

        return originalPos
    }
}
