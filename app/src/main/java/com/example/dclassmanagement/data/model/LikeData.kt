package com.example.dclassmanagement.data.model

data class LikeData(
    val postId: String,
    val userId: String,
){
    constructor() : this("", "")
}
