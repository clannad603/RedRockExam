package com.example.redrockexam.logic.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.model.bean.LoginInfo

@Database(version=1,entities = [LoginInfo::class,ContentInfo::class])
abstract class AppDatabase : RoomDatabase(){
    abstract fun personDao():LoginDao
    abstract fun contentDao():ContentDao
    companion object{
        private var instance:AppDatabase?=null
        @Synchronized
        fun getDatabase(context: Context):AppDatabase{
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,"app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build().apply {
                        instance=this
                    }
        }
    }
}