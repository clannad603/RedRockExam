package com.example.redrockexam.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class Permission(var context: Context) {
    var REQUEST_CODE = 1

    // 申请弹框权限
    fun requestAlertWindowsPermission(){
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent)
    }
}