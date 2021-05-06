package com.example.redrockexam.logic.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.redrockexam.R
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.TotoListApplication.Companion.context
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.model.constant.Constant
import com.example.redrockexam.logic.repository.ContentRepository
import com.example.redrockexam.ui.mainview.MainActivity
import com.example.redrockexam.utils.MyPreference
import com.example.redrockexam.utils.Permission
import com.example.redrockexam.utils.showToast
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.util.*

class AlumService : Service() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).contentDao()
    private val repository by lazy {
        ContentRepository(dao)
    }
    val tagList =MutableLiveData<MutableList<ContentInfo>>()
    var owner: String by MyPreference(Constant.KEY_OWNER, "")
    var title =MutableLiveData<String>()
    var content=MutableLiveData<String>()
    var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            get(owner)
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "my_service", "the service notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, "my_service")
            .setSmallIcon(R.drawable.add)
            .setContentTitle(title.value)
            .setContentText(content.value)
            .build()
        startForeground(1, notification)

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
    suspend fun get(owner:String) = coroutineScope {
        tagList.value=repository.loadTag(owner,"重要任务")
        val tagSize = tagList.value!!.size
        title.value=if (tagSize!=null) tagList.value!![0].title else " 123"
        content.value=if (tagSize!=null) tagList.value!![0].content else " 123"
    }
}