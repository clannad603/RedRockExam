package com.example.redrockexam.ui.account.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.TotoListApplication.Companion.context
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.LoginInfo
import com.example.redrockexam.logic.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginActivityViewModel : BaseViewModel() {
    val dao = AppDatabase.getDatabase(context).personDao()
    var userInfo = MutableLiveData<LoginInfo>()
    private val repository by lazy {
        LoginRepository(dao)
    }

    fun find(owner: String) = viewModelScope.launch {
        userInfo.value = repository.findOwner(owner)
    }

}