package com.example.dclassmanagement.data.model

data class User(
    val background: String,
    val email: String,
    val fullname: String,
    val id: String,
    val imageUrl: String,
    val password: String,
    val username: String
){
    constructor(): this("","","","","","","")
}