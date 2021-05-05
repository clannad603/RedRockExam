package com.example.redrockexam.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivitySearchBinding
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.ui.content.ContentListAdapter
import com.example.redrockexam.utils.StatusBarUtils
import com.example.redrockexam.utils.showToast

class SearchActivity : BaseActivity<SearchViewModel,ActivitySearchBinding>() {
    var adapter: ContentListAdapter?=null
    var list:ArrayList<ContentInfo>?=null
     var mKey:String?=null
    override fun initVM() {
        vm.titleList.observe(this, Observer {
            if (it!=null){
                list?.addAll(it)
                adapter!!.notifyDataSetChanged()
            }else{
                mKey?.let { it1 -> vm.getTagList(owner, it1) }
            }

        })
        vm.tagList.observe(this, Observer {
            if (it!=null){
                list?.addAll(it)
                adapter!!.notifyDataSetChanged()
            }
        })
        vm.listener.observe(this, Observer {
            "第${it}条信息被删除".showToast(this,"short")
        })
    }

    override fun initListener() {
        v.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
        adapter!!.itemClick {

        }
        adapter!!.itemLongClick {it->
            AlertDialog.Builder(mContext).apply {
                setTitle("Asking")
                setMessage("您是否想好要删除这个任务呢？")
                setCancelable(false)
                setPositiveButton("确定"){_,_,->
                    list?.remove(adapter!!.listData[it])
                    vm.deleteFromData(owner,adapter!!.listData[it].tag,adapter!!.listData[it].title)
                    adapter!!.notifyDataSetChanged()
                }
                setNegativeButton("取消"){it,_,->
                    it.dismiss()
                }
                show()
            }
        }
    }

    override fun initData() {

    }

    override fun initView() {
        setSupportActionBar(v.toolbar)
        list = ArrayList()
        adapter = ContentListAdapter(mContext,list!!)
        v.mRecyclerView.layoutManager= LinearLayoutManager(mContext)
        v.mRecyclerView.adapter=adapter
        StatusBarUtils.drawableStatusBar(this, R.color.white)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val mSearchView = searchItem.actionView as SearchView
        mSearchView.setIconifiedByDefault(true)

        mSearchView.isSubmitButtonEnabled = true

        mSearchView.imeOptions = EditorInfo.IME_ACTION_SEARCH

        mSearchView.isIconified = true

        mSearchView.isFocusable = true
        mSearchView.requestFocusFromTouch()

        mSearchView.queryHint = "请输入关键字"

        val editText = mSearchView.findViewById<EditText>(R.id.search_src_text)
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.hyperred))
        editText.setTextColor(ContextCompat.getColor(this, R.color.hyperred))


        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mKey = query
                vm.getSearch(owner, mKey!!)
                mSearchView.clearFocus()
                return false
            }


            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}