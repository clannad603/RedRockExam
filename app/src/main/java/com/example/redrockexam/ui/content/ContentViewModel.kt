package com.example.redrockexam.ui.content

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.repository.ContentRepository
import com.example.redrockexam.logic.repository.LoginRepository
import kotlinx.coroutines.launch
import java.security.acl.Owner

class ContentViewModel:BaseViewModel() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).contentDao()
    private val repository by lazy {
        ContentRepository(dao)
    }
    var tagList = MutableLiveData<MutableList<ContentInfo>>()
    fun getTagList(owner:String,tag:String) = viewModelScope.launch {
        repository.loadTag(owner, tag)
    }
}
