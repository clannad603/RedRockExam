package com.example.redrockexam.logic.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.redrockexam.R
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.model.constant.Constant
import com.example.redrockexam.logic.repository.ContentRepository
import com.example.redrockexam.ui.mainview.MainActivity
import com.example.redrockexam.utils.MyPreference
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class AlumService : Service() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).contentDao()
    private val repository by lazy {
        ContentRepository(dao)
    }
    val tagList =MutableLiveData<MutableList<ContentInfo>>()
    var owner: String by MyPreference(Constant.KEY_OWNER, "")
    var title:String =""
    var content:String=""

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
            .setContentTitle(title)
            .setContentText(content)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
    suspend fun get(owner:String) = coroutineScope {
        tagList.value=repository.loadTag(owner,"重要任务")
    }
}