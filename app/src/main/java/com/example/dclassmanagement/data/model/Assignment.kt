package com.example.dclassmanagement.data.model

import java.io.Serializable

data class Assignment(
    val id: String,
    val type: Int,
    val title: String,
    val content: String,
    val createdDate: Long,
    val dueDate: Long,
    val classId: String,
): Serializable{
    constructor(): this("",0,"","",0,0,"",)
}
