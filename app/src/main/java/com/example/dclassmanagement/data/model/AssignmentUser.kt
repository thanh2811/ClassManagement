package com.example.dclassmanagement.data.model

data class AssignmentUser(
    val id: String,
    val userId: String,
    val assignmentId: String,
    val submitDate: String,
    val status: Int
){
    constructor() : this("","","","",0)
}
