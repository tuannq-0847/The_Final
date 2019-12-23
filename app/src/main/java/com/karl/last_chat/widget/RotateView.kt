package com.karl.last_chat.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView


class RotateView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val angle = (360 / 12)

    init {
        ValueAnimator.ofInt(0, 12)
            .apply {
                addUpdateListener {
                    val animateValue: Int = (it.animatedValue as Int) * angle
                    val rotate = RotateAnimation(
                        animateValue.toFloat(),
                        animateValue.toFloat(),
                        Animation.RELATIVE_TO_SELF,
                        0.5F,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                    )
                    rotate.duration = 1000
                    rotate.interpolator = LinearInterpolator()
                    rotate.repeatCount = Animation.INFINITE
                    this@RotateView.startAnimation(rotate)
                }
                duration = 1000
                interpolator = LinearInterpolator()
                repeatCount = Animation.INFINITE
                start()
            }
    }
}
