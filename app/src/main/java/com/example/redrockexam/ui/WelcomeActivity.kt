package com.example.redrockexam.ui

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.ui.base.BaseViewModel
import com.example.redrockexam.R
import com.example.redrockexam.databinding.ActivityWelcomeBinding
import com.example.redrockexam.ui.account.login.LoginActivity
import com.example.redrockexam.ui.mainview.MainActivity
import com.example.redrockexam.logic.utils.StatusBarUtils

class WelcomeActivity : BaseActivity<BaseViewModel, ActivityWelcomeBinding>() {
    override fun initVM() {

    }

    override fun initListener() {

    }

    override fun initData() {

    }

    override fun initView() {
        StatusBarUtils.drawableStatusBar(this, R.color.white)
        ViewCompat.animate(v.imageView).apply {

            scaleX(1.0f)
            scaleY(1.0f)
            //动画时常1秒
            duration = 1000
            //动画监听
            setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationEnd(view: View?) { //动画结束
                    //进入主界面，并结束掉该页面
                    if (isLogin) {
                        startAnotherActivity(MainActivity::class.java)
                    } else {
                        startAnotherActivity(LoginActivity::class.java)
                    }
                    finish()
                }

                override fun onAnimationCancel(view: View?) {
                }

                override fun onAnimationStart(view: View?) {
                }
            })
        }
    }

}