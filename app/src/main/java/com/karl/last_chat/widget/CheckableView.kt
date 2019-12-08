package com.karl.last_chat.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


class CheckableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()
    private val tickablePaint = Paint()
    private var rect: RectF? = null
    private var leftR = 0F
    private var rightR = 0F
    private var bottomR = 0F
    private var topR = 0F
    private var angle = 0F
    private var path = Path()
    private var length = 0F

    init {
        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE
        paint.flags = Paint.ANTI_ALIAS_FLAG
        tickablePaint.style = Paint.Style.STROKE
        tickablePaint.flags = Paint.ANTI_ALIAS_FLAG
        paint.strokeWidth = 5F
        tickablePaint.strokeWidth = 5F
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        ValueAnimator.ofFloat(0F, 360F)
            .apply {
                addUpdateListener {
                    paint.color = Color.RED
                    tickablePaint.color = Color.RED
                    angle = it.animatedValue as Float
                    invalidate()
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        path.moveTo(((width / 3) - 10).toFloat(), ((height / 3) + 20).toFloat())
                        path.lineTo((width / 2).toFloat(), (height / 2).toFloat() + 25)
                        path.lineTo((2 * width / 3).toFloat(), (height / 3).toFloat())
                        val measure = PathMeasure(path, false)
                        length = measure.length
                        val animator =
                            ObjectAnimator.ofFloat(this@CheckableView, "phase", 1.0F, 0.0F)
                        animator.duration = 1500
                        animator.start()
                    }
                })
                duration = 3000
                start()
            }
    }

    //is called by animtor object
    fun setPhase(phase: Float) {
        Log.d("pathview", "setPhase called with:$phase")
        tickablePaint.pathEffect = createPathEffect(length, phase, 0.0f)
        invalidate()//will calll onDraw
    }

    private fun createPathEffect(pathLength: Float, phase: Float, offset: Float): PathEffect {
        return DashPathEffect(
            floatArrayOf(pathLength, pathLength),
            (phase * pathLength).coerceAtLeast(offset)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        leftR = (width / 3).toFloat() - 30
        topR = (height / 3).toFloat() - 30
        bottomR = (2 * height / 3).toFloat() + 30
        rightR = (2 * width / 3).toFloat() + 30
        rect = RectF(leftR, topR, rightR, bottomR)
        canvas?.drawArc(rect!!, 0F, angle, false, paint)
        canvas?.drawPath(path, tickablePaint)
    }
}
