package com.example.redrockexam.ui.mainview

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.LoginInfo
import com.example.redrockexam.logic.repository.LoginRepository
import kotlinx.coroutines.launch

class MainActivityViewModel:BaseViewModel() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).personDao()
    var userInfo  = MutableLiveData<LoginInfo>()
    var uri = MutableLiveData<Uri>()
    private val repository by lazy {
        LoginRepository(dao)
    }
    fun find(owner:String) = viewModelScope.launch {
        userInfo.value = repository.findOwner(owner)
    }
    fun update(owner: String,uri: String)=viewModelScope.launch {
        repository.updateUri(owner, uri)
    }
}