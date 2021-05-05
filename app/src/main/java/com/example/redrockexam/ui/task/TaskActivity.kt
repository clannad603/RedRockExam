package com.example.redrockexam.ui.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.databinding.ActivityTaskBinding
import com.example.redrockexam.utils.AnimationUtils
import com.example.redrockexam.utils.time2String
import java.util.*

class TaskActivity : BaseActivity<TaskViewModel,ActivityTaskBinding> (){
    private lateinit var choose:Date
    private lateinit var timePickerView: TimePickerView
    override fun initVM() {

    }

    override fun initListener() {
        v.etStartTime.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            val calendar = Calendar.getInstance()
            // 这里我们判断变量是否初始化
            if (this::choose.isInitialized){
                calendar.time = choose
            } else {
                calendar.time = Date()
            }
            timePickerView = TimePickerBuilder(mContext, null)
                .setTimeSelectChangeListener { date ->
                    // 设置当前的时间
                    choose = date
                    v.etStartTime.text = Editable.Factory.getInstance().newEditable(time2String(date))
                }
                .setType(booleanArrayOf(true,true,true,true,true,true))
                .isDialog(true)
                .setTitleText("时间选择")
                .setDate(calendar)
                .build()
            timePickerView.show()
        }
        }

    override fun initData() {

    }

    override fun initView() {

    }

}




