package com.example.dclassmanagement

import android.os.Bundle
import com.example.dclassmanagement.ui.base.BaseActivity
import com.example.dclassmanagement.ui.landing_page.FragmentLandingPage
import kotlinx.android.synthetic.main.activity_landing_page.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}