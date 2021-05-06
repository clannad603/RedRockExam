package com.example.redrockexam.logic.utils

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

fun String.showToast(context: Context, actionTime: String) {
    when (actionTime) {
        "long" -> Toast.makeText(context, this, Toast.LENGTH_LONG).show()
        "short" -> Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
}

fun Int.showToast(context: Context, actionTime: String) {
    when (actionTime) {
        "long" -> Toast.makeText(context, this, Toast.LENGTH_LONG).show()
        "short" -> Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
}

fun View.showSnackBar(
    text: String, actionText: String? = null,
    duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, text, duration)
    if (actionText != null && block != null) {
        snackbar.setAction(actionText) {
            block()
        }
    }
    snackbar.show()
}

fun View.showSnackBar(
    resId: Int, actionResId: Int? = null,
    duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, resId, duration)
    if (actionResId != null && block != null) {
        snackbar.setAction(actionResId) {
            block()
        }
    }
    snackbar.show()
}

fun showLongToast(context: Context?, message: String?) {
    if (!TextUtils.isEmpty(message)) Toast.makeText(context, message, Toast.LENGTH_LONG)
        .show()
}

fun showShortToast(context: Context?, message: String?) {
    if (!TextUtils.isEmpty(message)) Toast.makeText(context, message, Toast.LENGTH_SHORT)
        .show()
}