package com.example.redrockexam.ui.content

import android.content.Intent
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivityContentBinding
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.ui.detail.DetailActivity
import com.example.redrockexam.ui.mainview.ToDoListAdapter
import com.example.redrockexam.utils.StatusBarUtils
import com.example.redrockexam.utils.showToast

class ContentActivity : BaseActivity<ContentViewModel, ActivityContentBinding>() {
    var adapter: ContentListAdapter? = null
    var list: ArrayList<ContentInfo>? = null

    override fun initVM() {
        vm.tagList.observe(this, Observer {
            if (it != null) {
                list?.addAll(it)
            }
            adapter!!.notifyDataSetChanged()
        })
        vm.listener.observe(this, Observer {
            "第${it}条信息被删除".showToast(this, "short")
        })
    }

    override fun initListener() {

        v.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
        adapter!!.itemClick {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("tag", adapter!!.listData[it].tag)
                putExtra("title", adapter!!.listData[it].title)
                putExtra("start", adapter!!.listData[it].startTime)
                putExtra("end", adapter!!.listData[it].endTime)
                putExtra("content", adapter!!.listData[it].content)
            }
            startActivity(intent)
        }
        adapter!!.itemLongClick { it ->
            AlertDialog.Builder(mContext).apply {
                setTitle("Asking")
                setMessage("您是否想好要删除这个任务呢？")
                setCancelable(false)
                setPositiveButton("确定") { _, _ ->
                    list?.remove(adapter!!.listData[it])
                    vm.deleteFromData(
                        owner,
                        adapter!!.listData[it].tag,
                        adapter!!.listData[it].title
                    )
                    adapter!!.notifyDataSetChanged()
                }
                setNegativeButton("取消") { it, _ ->
                    it.dismiss()
                }
                show()
            }
        }
    }

    override fun initData() {


    }

    override fun initView() {
        val tag = intent.getStringExtra("TAG")
        v.toolbar.title = tag
        list = ArrayList()
        vm.getTagList(owner, tag!!)
        adapter = ContentListAdapter(mContext, list!!)
        v.mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        v.mRecyclerView.adapter = adapter
        StatusBarUtils.drawableStatusBar(this, R.color.white)
    }

}