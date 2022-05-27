package com.example.dclassmanagement.data.model

import java.io.Serializable

data class Post(
    val id: String,
    val ownerId: String,
    val classId: String,
    val type: Long,
    var thumb: String,
    val content: String,
    val createdDate: Long
): Serializable{

    constructor() : this("","","",0,"","",0)

}