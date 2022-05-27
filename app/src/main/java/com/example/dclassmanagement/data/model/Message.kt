package com.example.dclassmanagement.data.model

data class Message(

    val msg: String,
    val chatId: String,
    val sentBy: String,
    val sentAt: Long
){
    constructor(): this("","","",0)
}
