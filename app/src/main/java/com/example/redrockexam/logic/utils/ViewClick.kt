package com.example.redrockexam.logic.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.redrockexam.logic.utils.ViewClickDelay.SPACE_TIME
import com.example.redrockexam.logic.utils.ViewClickDelay.hash

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
/**
 * 添加点击缩放效果
 * 百度获取，未经过测试，后期测试
 */
@SuppressLint("ClickableViewAccessibility")
fun View.addClickScale(scale: Float = 0.9f, duration: Long = 150) {
    this.setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.animate().scaleX(1f).scaleY(1f).setDuration(duration).start()
            }
        }
        // 点击事件处理，交给View自身
        this.onTouchEvent(event)
    }
}
