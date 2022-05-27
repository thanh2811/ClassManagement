package com.example.dclassmanagement.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    private val baseLoadingDialog by lazy {
        BaseLoadingDialog()
    }

    open fun initData(){}
    open fun initView(){}
    open fun initListener(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        initListener()
    }

    fun showLoading(){
        baseLoadingDialog.show(childFragmentManager, null)
    }

    fun hideLoading(){
        baseLoadingDialog.dismiss()
    }

    fun showToast(message: String){
        Toast.makeText(activity?.baseContext, message, Toast.LENGTH_SHORT)
            .show()
    }

}