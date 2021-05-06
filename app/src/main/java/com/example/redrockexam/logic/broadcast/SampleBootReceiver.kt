package com.example.redrockexam.logic.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.redrockexam.logic.service.AlumService
import com.example.redrockexam.utils.showToast


class SampleBootReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action!=null && intent.action == "android.intent.action.BOOT_COMPLETED") {
            "Alarm Manager just ran".showToast(context,"short")
        }else{
            val serviceIntent = Intent(context, AlumService::class.java)
            context.startService(serviceIntent)
        }
    }
}