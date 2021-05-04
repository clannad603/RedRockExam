package com.example.myapplication.ui.base

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

import com.example.redrockexam.utils.clicks

import java.lang.reflect.ParameterizedType

abstract class BaseAdapter <VB : ViewBinding, T>(
        var mContext: Activity,
        var listData:  ArrayList<T>
) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun getItemCount(): Int {
        return listData.size
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.itemView.clicks {
            itemClick?.let { it(position) }
        }
        holder.itemView.setOnLongClickListener {
            itemLongClick?.let { it1 -> it1(position) }
            true
        }
       convert(holder.v as VB, listData[position], position)
    }
    abstract fun convert(v: VB, t: T, position: Int)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        var vb = method.invoke(null, LayoutInflater.from(mContext)) as VB
        vb.root.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return BaseViewHolder(vb, vb.root)
    }
    private var itemClick: ((Int) -> Unit)? = null
    private var itemLongClick: ((Int) -> Unit)? = null
    fun itemClick(itemClick: (Int) -> Unit) {
        this.itemClick = itemClick
    }

    fun itemLongClick(itemLongClick: (Int) -> Unit) {
        this.itemLongClick = itemLongClick
    }

}