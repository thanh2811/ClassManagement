package com.example.dclassmanagement.data.api

import com.example.dclassmanagement.data.model.MarkRequest
import com.example.dclassmanagement.data.model.MarkResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {

    @POST("/asignment/mark/multi-choice")
    fun getScore(@Body markRequest: MarkRequest, @Header("Content-Type") type: String = "application/json"): Call<MarkResponse>

}