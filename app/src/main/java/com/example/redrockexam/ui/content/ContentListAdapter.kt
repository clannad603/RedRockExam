package com.example.redrockexam.ui.content

import android.app.Activity
import com.example.myapplication.ui.base.BaseAdapter
import com.example.redrockexam.databinding.ContentListStyleBinding
import com.example.redrockexam.databinding.ItemFormBinding
import com.example.redrockexam.logic.model.bean.ContentInfo

class ContentListAdapter(context: Activity, listDatas: ArrayList<ContentInfo>) :
    BaseAdapter<ContentListStyleBinding, ContentInfo>(context, listDatas) {
    override fun convert(v: ContentListStyleBinding, t: ContentInfo, position: Int) {
        v.textView.text = t.title
        v.tvTag.text=t.tag
    }
}
