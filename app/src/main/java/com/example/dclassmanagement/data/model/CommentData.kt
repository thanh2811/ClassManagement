package com.example.dclassmanagement.data.model

data class CommentData(
    val postId: String,
    val userId: String,
    val content: String
){
    constructor() : this("", "", "")
}
