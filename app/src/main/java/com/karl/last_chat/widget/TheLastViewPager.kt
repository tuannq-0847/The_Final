package com.karl.last_chat.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import androidx.core.view.MotionEventCompat
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class TheLastViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    var mStartDragX: Float = 0F
    private lateinit var mListener: OnSwipeOutListener


    fun setOnSwipeOutListener(listener: OnSwipeOutListener) {
        mListener = listener
    }


    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (currentItem == adapter!!.count - 1) {
            val action = ev.action
            val x = ev.x
            when (action and MotionEventCompat.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> mStartDragX = x
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> if (x < mStartDragX) {
                    mListener.onSwipeOutAtEnd()
                } else {
                    mStartDragX = 0F
                }
            }
        } else {
            mStartDragX = 0F
        }
        return super.onTouchEvent(ev)
    }

    interface OnSwipeOutListener {
        fun onSwipeOutAtStart()
        fun onSwipeOutAtEnd()
    }
}