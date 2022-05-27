package com.example.dclassmanagement.data.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun createId(): String{
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..20)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun getDateFromMillisecond(millis: Long): String{
        val c = Calendar.getInstance()
        c.timeInMillis = millis
        val dateFormat = SimpleDateFormat("hh:mm - dd/MM/yyyy")
        return dateFormat.format(c.time)
    }
}