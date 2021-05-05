package com.example.redrockexam.ui.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivityTaskBinding
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.utils.AnimationUtils
import com.example.redrockexam.utils.StatusBarUtils
import com.example.redrockexam.utils.showToast
import com.example.redrockexam.utils.time2String
import java.util.*

class TaskActivity : BaseActivity<TaskViewModel, ActivityTaskBinding>() {
    private lateinit var choose: Date
    private lateinit var timePickerView: TimePickerView
    override fun initVM() {
        vm.numOfTheApp.observe(this, androidx.lifecycle.Observer {
            "第${it}条信息注入成功".showToast(this,"short")
            finish()
        })
        vm.numOfUpdate.observe(this, androidx.lifecycle.Observer {
            "第${it}条数据更新成功".showToast(this,"short")
             finish()
        })
    }

    override fun initListener() {
        v.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
        v.btnAddTime.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            initDialog(v.etStartTime)
        }
        v.btnEndTime.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            initDialog(v.etEndTime)
        }
        v.flABtnAdd.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            attemptInsert()
        }
    }


    override fun initData() {

    }

    override fun initView() {
        StatusBarUtils.drawableStatusBar(this, R.color.hypergreen)
    }

    private fun initDialog(et: EditText) {
        val calendar = Calendar.getInstance()
        // 这里我们判断变量是否初始化
        if (this::choose.isInitialized) {
            calendar.time = choose
        } else {
            calendar.time = Date()
        }
        timePickerView = TimePickerBuilder(mContext, null)
            .setTimeSelectChangeListener { date ->
                // 设置当前的时间
                choose = date
                et.text = Editable.Factory.getInstance().newEditable(time2String(date))
            }
            .setType(booleanArrayOf(true, true, true, true, true, true))
            .isDialog(true)
            .setTitleText("时间选择")
            .setDate(calendar)
            .build()
        timePickerView.show()
    }
    private fun attemptInsert() {
        val tag = v.etAddTag.text.toString()
        val titil = v.etAddTitile.text.toString()
        val addStartTime = v.etStartTime.text.toString()
        val addEndTime = v.etEndTime.text.toString()
        val content = v.etAddContent.text.toString()
        if (tag .isEmpty())
        {
            "请输入分类".showToast(this,"short")
        }
        else if (titil.isEmpty()){
            "请输入标签".showToast(this,"short")
        }else if (addStartTime.isEmpty()){
            "请输入开始时间".showToast(this,"short")
        }else if(addEndTime.isEmpty()){
            "请输入结束时间".showToast(this,"short")
        }else if (content.isEmpty()){
            "请输入内容".showToast(this,"short")
        }else{
                doInsert(tag,titil,addStartTime,addEndTime,content)
        }

    }
    private fun doInsert(tag:String,titil:String,startTime:String,endTime:String,content:String){
        vm.find(owner,tag,titil)
        val contentInfo = ContentInfo(owner,tag,startTime,endTime,titil,content)
        vm.contentInfo.observe(this, androidx.lifecycle.Observer {
            if (it!=null && it.content!=content){
                "此事件已存在，为您更新数据".showToast(this,"short")
                vm.update(owner,tag,titil,content)
            }else if (it==null){
                "为您注入事件".showToast(this,"short")
                vm.insert(contentInfo)
            }
        })
    }
}





