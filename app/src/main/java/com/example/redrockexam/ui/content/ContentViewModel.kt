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

class ContentViewModel : BaseViewModel() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).contentDao()
    private val repository by lazy {
        ContentRepository(dao)
    }
    val listener = MutableLiveData<Int>()
    fun deleteFromData(owner: String, tag: String, title: String) = viewModelScope.launch {
        listener.value = repository.deleteInfo(owner, tag, title)
    }
    var tagList = MutableLiveData<List<ContentInfo>>()
    fun getTagList(owner: String, tag: String) = viewModelScope.launch {
        tagList.value = repository.loadTag(owner, tag)
    }

}
