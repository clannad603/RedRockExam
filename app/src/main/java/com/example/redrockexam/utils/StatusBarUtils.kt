package com.example.redrockexam.utils

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import java.lang.reflect.Field


object StatusBarUtils {
    /**
     * 绘制状态栏
     * @param act
     * @param color
     */
    fun drawableStatusBar(act: Activity, color: Int) {
        transparentStatusBar(act, true)
        addStatusBar(act, color)
    }

    /**
     * 透明化状态栏
     *
     * @param act
     */
    fun transparentStatusBar(act: Activity, fitWindow: Boolean) {
        val window: Window = act.window

        //透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        //获取根布局
        val root = act.findViewById<View>(R.id.content) as ViewGroup
        val firstChild: View? = root.getChildAt(0)
        if (firstChild != null) {
            //设置 root 的第一个子 View . 使其为系统 View 预留空间.
            firstChild.setFitsSystemWindows(fitWindow)
        }
    }

    /**
     * 添加状态栏
     *
     * @param act
     * @param color
     */
    fun addStatusBar(act: Activity, color: Int) {
        val resource = act.resources.getColor(color)
        //创建状态栏
        val statusBar: View = createStatusBar(act, resource)

        //获取根布局
        val root = act.findViewById<View>(R.id.content) as ViewGroup
        //将状态栏添加到根布局下的第一个子布局位置
        root.addView(statusBar, 0)
    }

    /**
     * 创建一个状态栏
     *
     * @param context
     * @param color
     * @return
     */
    fun createStatusBar(context: Context, color: Int): View {

        //方法原理:
        //添加一个和状态栏高、宽相同的指定颜色的View来覆盖被透明化的状态栏
        val statusBar = View(context)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(context)
        )
        statusBar.setLayoutParams(params)
        statusBar.setBackgroundColor(color)
        return statusBar
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    @SuppressLint("PrivateApi")
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            //通过反射获取状态栏高度这个成员变量
            val status_bar_height: Field = clazz.getField("status_bar_height")
            val obj = clazz.newInstance()
            val height: Int = status_bar_height.get(obj).toString().toInt()
            statusBarHeight = context.getResources().getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusBarHeight
    }
}