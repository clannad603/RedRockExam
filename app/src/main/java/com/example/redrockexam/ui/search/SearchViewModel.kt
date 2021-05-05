package com.example.redrockexam.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.repository.ContentRepository
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).contentDao()
    private val repository by lazy {
        ContentRepository(dao)
    }
    val listener = MutableLiveData<Int>()
    fun deleteFromData(owner: String, tag: String, title: String) = viewModelScope.launch {
        listener.value = repository.deleteInfo(owner, tag, title)
    }

    var titleList = MutableLiveData<List<ContentInfo>>()
    fun getSearch(owner: String, title: String) = viewModelScope.launch {
        titleList.value = repository.getFromTitle(owner, title)
    }

}