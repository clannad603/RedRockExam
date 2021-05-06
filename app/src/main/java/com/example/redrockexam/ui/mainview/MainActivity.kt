package com.example.redrockexam.ui.mainview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivityMainBinding
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.model.constant.Constant
import com.example.redrockexam.ui.content.ContentActivity
import com.example.redrockexam.ui.search.SearchActivity
import com.example.redrockexam.ui.task.TaskActivity
import com.example.redrockexam.logic.utils.*


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    val fromALbum = 1
    var adapter: ToDoListAdapter? = null
    var list: ArrayList<String>? = null
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
        vm._tagList.observe(this, Observer {

            if (it != null) {
                for (tags in it) {
                    if (tags.tag != "我的一天"
                        && tags.tag != "总任务"
                        && tags.tag != "重要任务"
                        && tags.tag != "不太重要任务"
                    ) {
                        list?.add(tags.tag)
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun initListener() {
        v.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.homeSearch -> startAnotherActivity(SearchActivity::class.java)
            }
            true
        }
        adapter!!.itemClick {
            val intent = Intent(this, ContentActivity::class.java).apply {
                putExtra("TAG", adapter!!.listData[it])
                Log.d("main", adapter!!.listData[it])
            }
            startActivity(intent)
        }
        adapter!!.itemLongClick { it ->
            AlertDialog.Builder(mContext).apply {
                setTitle("Asking")
                setMessage("您是否想好要删除这个清单呢？")
                setCancelable(false)
                setPositiveButton("确定") { _, _ ->
                    val tag = adapter!!.listData[it]
                    if ( tag!= "我的一天"
                        && tag != "总任务"
                        && tag != "重要任务"
                        && tag != "不太重要任务"
                    ){
                        list?.remove(tag)
                        vm.deleteTag(owner, tag)
                        adapter!!.notifyDataSetChanged()
                    }else{
                        "内置模块，不能删除".showToast(mContext,"short")
                    }


                }
                setNegativeButton("取消") { it, _ ->
                    it.dismiss()
                }
                show()
            }

        }
        v.btnNew.setOnClickListener {
            AnimationUtils.clickAnimation(it)
            startAnotherActivity(TaskActivity::class.java)
        }
        v.flABtnAdd.setOnClickListener {
            AnimationUtils.clickAnimation(it)
            val dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_style, null)
            AlertDialog.Builder(mContext).apply {
                setTitle("Asking")
                setMessage("您是否想好要添加一个清单呢？")
                setView(dialogView)
                setCancelable(false)
                setPositiveButton("确定") { it, _ ->
                    val edit = dialogView.findViewById(R.id.tv_tag) as EditText
                    list?.add(edit.text.toString())
                    adapter!!.notifyDataSetChanged()
                    val contentInfo = ContentInfo(owner, edit.text.toString(), null, null, "", " ")
                    vm.insert(contentInfo)
                }
                setNegativeButton("取消") { it, _ ->
                    it.dismiss()
                }
                show()
            }
        }
        v.btnOut.setOnClickListener {
            AnimationUtils.clickAnimation(it)
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
            AnimationUtils.clickAnimation(it)
            "禁止在乱点".showToast(this, "short")
        }
    }

    override fun initData() {

            val intent = Intent("com.example.MYACTIVITY_START")
            intent.setPackage(packageName)
            sendBroadcast(intent)

    }

    override fun initView() {
        list = ArrayList()
        initList()
        adapter = ToDoListAdapter(mContext, list!!)
        v.mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        v.mRecyclerView.adapter = adapter
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

    private fun initList() {
        list?.add("我的一天")
        list?.add("总任务")
        list?.add("重要任务")
        list?.add("不太重要任务")
        vm.getTag(owner)
    }

    override fun onPause() {
        super.onPause()

    }
}