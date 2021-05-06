package com.example.redrockexam.ui.detail

import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivityDetailBinding
import com.example.redrockexam.logic.utils.StatusBarUtils

class DetailActivity : BaseActivity<BaseViewModel, ActivityDetailBinding>() {
    override fun initVM() {

    }

    override fun initListener() {
        v.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    override fun initData() {

    }

    override fun initView() {
        StatusBarUtils.drawableStatusBar(this, R.color.hypergreen)
        val tag = intent.getStringExtra("tag")
        v.tvTag.text = tag
        val start = intent.getStringExtra("start")
        v.tvStart.text = start.toString()
        val end = intent.getStringExtra("end")
        v.tvEnd.text = end
        val content = intent.getStringExtra("content")
        v.tvContent.text = content
        val title = intent.getStringExtra("title")
        v.tvTitle.text = title
        v.toolbar.title = title
    }
}