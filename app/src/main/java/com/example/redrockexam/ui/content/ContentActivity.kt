package com.example.redrockexam.ui.content

import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.databinding.ActivityContentBinding
import com.example.redrockexam.utils.StatusBarUtils

class ContentActivity : BaseActivity<ContentViewModel,ActivityContentBinding>() {
    companion object {
        const val TAG = "重要任务"
    }

    override fun initVM() {

    }

    override fun initListener() {

    }

    override fun initData() {
        intent?.extras?.getString(TAG).let {
          vm.getTagList(owner, TAG)
        }
    }

    override fun initView() {
        StatusBarUtils.transparentStatusBar(this,true)
        v.toolbar.title = TAG
    }

}