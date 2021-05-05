package com.example.redrockexam.ui.mainview

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.model.bean.LoginInfo
import com.example.redrockexam.logic.repository.ContentRepository
import com.example.redrockexam.logic.repository.LoginRepository
import kotlinx.coroutines.launch

class MainActivityViewModel : BaseViewModel() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).personDao()
    var userInfo = MutableLiveData<LoginInfo>()
    var uri = MutableLiveData<Uri>()
    val dao1 = AppDatabase.getDatabase(TotoListApplication.context).contentDao()
    private val repository1 by lazy {
        ContentRepository(dao1)
    }
    var _tagList = MutableLiveData<MutableList<ContentInfo>>()

    private val repository by lazy {
        LoginRepository(dao)
    }

    fun find(owner: String) = viewModelScope.launch {
        userInfo.value = repository.findOwner(owner)
    }

    fun update(owner: String, uri: String) = viewModelScope.launch {
        repository.updateUri(owner, uri)
    }

    fun getTag(owner: String) = viewModelScope.launch {
        _tagList.value = repository1.getTag(owner)
    }

    fun deleteTag(owner: String, tag: String) = viewModelScope.launch {
        repository1.deleteTag(owner, tag)
    }
}