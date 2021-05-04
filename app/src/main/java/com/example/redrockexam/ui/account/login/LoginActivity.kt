package com.example.redrockexam.ui.account.login

import androidx.lifecycle.Observer
import com.example.myapplication.ui.base.BaseActivity
import com.example.redrockexam.ui.mainview.MainActivity
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivityLoginBinding
import com.example.redrockexam.ui.account.register.RegisterActivity
import com.example.redrockexam.utils.AnimationUtils
import com.example.redrockexam.utils.StatusBarUtils
import com.example.redrockexam.utils.showToast

class LoginActivity : BaseActivity<LoginActivityViewModel, ActivityLoginBinding>() {
    override fun initVM() {
    }

    override fun initListener() {
        v.btnRegister.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            startAnotherActivity(RegisterActivity::class.java)
        }
        v.btnLogin.setOnClickListener {
            AnimationUtils.buttonClickAnimation(it)
            attemptLogin()
        }
    }

    override fun initData() {

    }

    override fun initView() {
    StatusBarUtils.drawableStatusBar(this,R.color.white)
    }

    private fun attemptLogin() {
        val username = v.editTextTextAccount.text.toString()
        val password = v.editTextTextPassword.text.toString()

        if (password.isEmpty()) {
            "请输入密码".showToast(this, "short")
        } else if (username.isEmpty()) {
            "请输入用户名".showToast(this, "short")

        } else {
            doLogin(username, password)
        }
    }

    private fun doLogin(username: String, password: String) {
        vm.find(username)
        vm.userInfo.observe(this, Observer {
            if (it!=null && it.password == password){
                isLogin = true
                owner = username
                startAnotherActivity(MainActivity::class.java)
            } else {
                "数据库中未查询到，请注册后在登录".showToast(this, "short")
            }
        })
    }
}