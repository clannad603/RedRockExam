package com.example.redrockexam.ui.task

import androidx.lifecycle.MutableLiveData
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
    var numOfUpdate = MutableLiveData<Int>()
    var numOfTheApp  = MutableLiveData<Long>()
    val contentInfo  =MutableLiveData<ContentInfo>()
    fun insert(contentInfo: ContentInfo) = viewModelScope.launch {
         numOfTheApp.value = repository.insert(contentInfo)
    }
    fun find(owner:String,tag:String,titile:String)=viewModelScope.launch {
        contentInfo.value=repository.find(owner,tag,titile)
    }
    fun update(owner: String,tag: String,titile: String,content:String)=viewModelScope.launch {
       numOfUpdate.value= repository.updateDetail(owner,tag,titile,content)
    }
}