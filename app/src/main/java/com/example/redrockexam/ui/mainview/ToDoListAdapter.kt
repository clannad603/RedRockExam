package com.example.redrockexam.ui.mainview

import android.app.Activity
import android.content.Context
import com.example.myapplication.ui.base.BaseAdapter
import com.example.redrockexam.databinding.ItemFormBinding

class ToDoListAdapter (context: Activity,listDatas:ArrayList<String>):
BaseAdapter<ItemFormBinding,String>(context,listDatas){
    override fun convert(v: ItemFormBinding, t: String, position: Int) {
       v.textView.text=t
    }
}