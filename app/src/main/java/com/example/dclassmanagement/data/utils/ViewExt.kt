package com.example.dclassmanagement.data.utils

import android.text.TextUtils
import android.view.View
import android.widget.TextView

fun TextView.setMarquee(){
    isSelected = true
    ellipsize = TextUtils.TruncateAt.MARQUEE
    isSingleLine = true
}

fun TextView.disableMarquee(){
    isSelected = false
    ellipsize = null
//    isSingleLine = false
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}