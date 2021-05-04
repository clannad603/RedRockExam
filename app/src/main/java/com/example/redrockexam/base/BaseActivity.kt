package com.example.myapplication.ui.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle

import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.redrockexam.logic.model.constant.Constant


import com.example.redrockexam.utils.MyPreference

import java.lang.reflect.ParameterizedType

/***
 * @Override
public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
super.onCreate(savedInstanceState, persistentState);
}
 此方法导致界面不显示，原因
 */
abstract class BaseActivity <VM : BaseViewModel, VB : ViewBinding> :AppCompatActivity(){
    lateinit var mContext: FragmentActivity
    lateinit var vm: VM
    lateinit var v: VB
    var isLogin: Boolean by MyPreference(Constant.KEY_LOGIN, false)
    var owner:String by MyPreference(Constant.KEY_OWNER,"")
    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = type.actualTypeArguments[0] as Class<VM>
        vm = ViewModelProvider(this).get(clazz1)
        /***
         * 使用vm来调用viewmodel
         */
        val clazz2 = type.actualTypeArguments[1] as Class<VB>
        val method = clazz2.getMethod("inflate", LayoutInflater::class.java)
        v = method.invoke(null, layoutInflater) as VB

        setContentView(v.root)
        /***
         * 可直接使用v.控件来调用方法
         */
        mContext = this
        initView()
        initData()
        initVM()
        initListener()
    }


    abstract fun initVM()

    fun startAnotherActivity(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

//    /***
//     * 初始化碎片
//     */
//    fun  startFragment( id:Int,fragment: BaseFragment){
//        val fragmentManager=supportFragmentManager
//        val transaction=fragmentManager.beginTransaction()
//        transaction.add(id,fragment)
//        transaction.commit()
//    }
    abstract fun initListener()

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun initData()

    abstract fun initView()

}