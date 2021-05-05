package com.example.myapplication.ui.base

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redrockexam.TotoListApplication
import com.example.redrockexam.logic.dao.AppDatabase
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.repository.ContentRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

}