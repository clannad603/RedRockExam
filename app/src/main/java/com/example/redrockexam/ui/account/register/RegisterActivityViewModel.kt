package com.example.redrockexam.ui.account.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.LoginInfo
import com.example.redrockexam.logic.repository.LoginRepository
import kotlinx.coroutines.launch

class RegisterActivityViewModel:BaseViewModel() {
    val dao = AppDatabase.getDatabase(TotoListApplication.context).personDao()
    private val repository by lazy {
        LoginRepository(dao)
    }
    var numOfTheApp  = MutableLiveData<Long>()
    fun insert(loginInfo: LoginInfo) = viewModelScope.launch {
       numOfTheApp.value= repository.insert(loginInfo)
    }
    var userInfo  = MutableLiveData<LoginInfo>()
    fun find(owner:String) = viewModelScope.launch {
        userInfo.value = repository.findOwner(owner)
    }
}