package com.example.dclassmanagement.ui.class_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.FragmentLandingPageARGS
import com.example.dclassmanagement.ui.base.BaseFragment

class FragmentAssignment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initData() {
        val classId = arguments?.getString(FragmentLandingPageARGS.KEY.CLASS_ID)
        super.initData()
    }

    override fun initView() {
        super.initView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



}