package com.example.redrockexam.logic.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun time2String(time: Date): String {
    return SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(time)
}