package com.example.dclassmanagement.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    private val baseLoadingDialog by lazy {
        BaseLoadingDialog()
    }

    open fun initData(){}
    open fun initView(){}
    open fun initListener(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        initData()
        initView()
        initListener()
    }

    fun showLoading(){
        baseLoadingDialog.show(supportFragmentManager, null)
    }

    fun hideLoading(){
        baseLoadingDialog.dismiss()
    }

    fun showToast(message: String){
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT)
            .show()
    }

    fun fragmentReplacer(container: Int, fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(container, fragment, null).commit()
    }
}