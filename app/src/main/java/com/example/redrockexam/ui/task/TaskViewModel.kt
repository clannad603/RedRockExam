package com.example.redrockexam.ui.task

import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.repository.ContentRepository
import kotlinx.coroutines.launch

class TaskViewModel:BaseViewModel() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).contentDao()
    private val repository by lazy {
        ContentRepository(dao)
    }
    fun insert(contentInfo: ContentInfo) = viewModelScope.launch {
        repository.insert(contentInfo)
    }
}