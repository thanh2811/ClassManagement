package com.example.dclassmanagement.data.utils

object Utils {

    fun createId(): String{
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..20)
            .map { allowedChars.random() }
            .joinToString("")
    }

}