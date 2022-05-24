package com.example.dclassmanagement.data.model

data class UserClass(
    val classId: String,
    val userId: String
){
    constructor(): this("","")
}