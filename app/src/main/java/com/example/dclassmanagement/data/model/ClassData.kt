package com.example.dclassmanagement.data.model

data class ClassData(
    val id: String,
    val name: String,
    val des: String,
    val ownerId: String,
    val createdDate: String,
    val schedule: String,
    val thumb: String
){
    constructor(): this("","","","","","","")
}
