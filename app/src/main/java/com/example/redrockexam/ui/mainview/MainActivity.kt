package com.example.redrockexam.ui.mainview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.R
import com.example.redrockexam.TotoListApplication.Companion.context
import com.example.redrockexam.databinding.ActivityMainBinding
import com.example.redrockexam.logic.model.bean.LoginInfo
import com.example.redrockexam.ui.account.login.LoginActivity
import com.example.redrockexam.ui.content.ContentActivity
import com.example.redrockexam.ui.task.TaskActivity
import com.example.redrockexam.utils.*


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
        adapter!!.itemClick {
            val intent = Intent(mContext, ContentActivity::class.java).apply {
                putExtra(ContentActivity.TAG, adapter!!.listData[it])
            }
            startActivity(intent)
        }
        adapter!!.itemLongClick {it->
            AlertDialog.Builder(mContext).apply {
                setTitle("Asking")
                setMessage("您是否想好要删除这个清单呢？")
                setCancelable(false)
                setPositiveButton("确定"){_,_,->
                    list?.remove(adapter!!.listData[it])
                    adapter!!.notifyDataSetChanged()
                }
                setNegativeButton("取消"){it,_,->
                    it.dismiss()
                }
                show()
            }

        }
        v.btnNew.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            startAnotherActivity(TaskActivity::class.java)
        }
        v.flABtnAdd.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            val dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_style,null)
            AlertDialog.Builder(mContext).apply {
                setTitle("Asking")
                setMessage("您是否想好要添加一个清单呢？")
                setView(dialogView)
                setCancelable(false)
                setPositiveButton("确定"){it,_,->
                   val edit = dialogView.findViewById(R.id.tv_tag) as EditText
                    list?.add(edit.text.toString())
                    adapter!!.notifyDataSetChanged()
                }
                setNegativeButton("取消"){it,_,->
                    it.dismiss()
                }
                show()
            }
        }
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
        v.imageViewUser.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            "禁止在乱点".showToast(this,"short")
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