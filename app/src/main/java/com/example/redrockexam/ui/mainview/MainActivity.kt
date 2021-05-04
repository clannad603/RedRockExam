package com.example.redrockexam.ui.mainview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivityMainBinding
import com.example.redrockexam.logic.model.bean.LoginInfo
import com.example.redrockexam.utils.AnimationUtils
import com.example.redrockexam.utils.StatusBarUtils


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    val fromALbum = 1
    var adapter:ToDoListAdapter?=null
    var list:ArrayList<String>?=null
    override fun initVM() {
        vm.find(owner)
        vm.userInfo.observe(this, Observer {
            it.uri.let { uri ->
                Glide.with(this).load(Uri.parse(uri)).apply(
                    RequestOptions.bitmapTransform(
                        CircleCrop()
                    )
                ).into(v.imageViewUser)
            }
        })
        vm.uri.observe(this, Observer { uri ->
            val string = uri.toString()
            vm.update(owner, string)
            Glide.with(this).load(uri).apply(
                RequestOptions.bitmapTransform(
                    CircleCrop()
                )
            ).into(v.imageViewUser)
        })
    }

    override fun initListener() {

        v.btnOut.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            val intent = Intent("com.example.FORCE_OFFLINE")
            sendBroadcast(intent)
        }
        v.imageViewUser.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, fromALbum)
            true
        }
    }

    override fun initData() {

    }

    override fun initView() {
        list = ArrayList()
        initList()
        adapter = ToDoListAdapter(mContext,list!!)
        v.mRecyclerView.layoutManager=LinearLayoutManager(mContext)
        v.mRecyclerView.adapter=adapter
        StatusBarUtils.drawableStatusBar(this, R.color.white)
        v.tvUsername.text = owner
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            fromALbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let {
                        vm.uri.value = it
                    }
                }
            }
        }
    }

    private fun initList(){
        list?.add("我的一天")
        list?.add("总任务")
        list?.add("重要任务")
        list?.add("不太重要任务")
    }
}