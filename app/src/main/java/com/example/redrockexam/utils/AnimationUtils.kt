package com.example.redrockexam.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator

object AnimationUtils {
    fun buttonClickAnimation(v: View) {
        val animator = ObjectAnimator.ofFloat(v, "scaleX", 1f, 0.8f)
        animator.duration = 150
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = 1
        animator.repeatMode = ValueAnimator.REVERSE

        val animator2 = ObjectAnimator.ofFloat(v, "scaleY", 1f, 0.8f)
        animator2.duration = 150
        animator2.interpolator = LinearInterpolator()
        animator2.repeatCount = 1
        animator2.repeatMode = ValueAnimator.REVERSE

        val animatorSet = AnimatorSet()
        animatorSet.play(animator).with(animator2)
        animatorSet.start()
    }
}