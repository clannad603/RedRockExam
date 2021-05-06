package com.example.redrockexam.ui.account.register

import androidx.lifecycle.Observer
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivityRegisterBinding
import com.example.redrockexam.logic.model.bean.LoginInfo
import com.example.redrockexam.logic.utils.*

class RegisterActivity : BaseActivity<RegisterActivityViewModel, ActivityRegisterBinding>() {
    override fun initVM() {
        vm.numOfTheApp.observe(this, Observer {

            showShortToast(this, "你是第${it}注册")
            finish()
        })
    }

    override fun initListener() {
        v.btnRegister.setOnclickNoRepeat {
            attemptRegister()
        }
        v.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    override fun initData() {

    }

    override fun initView() {
        StatusBarUtils.drawableStatusBar(this, R.color.white)
    }

    private fun attemptRegister() {
        val username = v.etUsername.text.toString()
        val password = v.etPassword.text.toString()
        val repassword = v.etRepassword.text.toString()
        if (repassword.isEmpty()) {
            showLongToast(this, "请确认密码")
        } else if (password.isEmpty()) {
            showLongToast(this, "请输入密码")
        } else if (password != repassword) {
            showLongToast(this, "密码不一致")
        } else if (username.isEmpty()) {
            showLongToast(this, "请输入用户名")

        } else {
            doRegister(username, password, repassword)
        }

    }

    private fun doRegister(username: String, password: String, uri: String) {
        vm.find(username)
        val registerInfo = LoginInfo(username, password, "")
        vm.userInfo.observe(this, Observer {
            if (it != null && it.password == password) {
                "数据库中已经存在此账户".showToast(this, "short")
            } else {
                vm.insert(registerInfo)
            }
        })


    }
}