package com.example.redrockexam.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.redrockexam.utils.ViewClickDelay.SPACE_TIME
import com.example.redrockexam.utils.ViewClickDelay.hash

object ViewClickDelay {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var SPACE_TIME: Long = 1000
}

infix fun View.clicks(clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > SPACE_TIME) {
                lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}

/**
 * 防止重复点击
 */
var lastClickTime = 0L
fun View.setOnclickNoRepeat(interval: Long = 500, onClick: (View) -> Unit) {
    this.setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        onClick.invoke(it)
    }
}
