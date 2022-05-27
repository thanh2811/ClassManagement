package com.example.dclassmanagement.data.model

data class Chat(
    val id: String,
    val uid1: String,
    val uid2: String,
    val lastMessage: String,
    val lastSentDate: Long
){
    constructor(): this("","","","", 0)
}
