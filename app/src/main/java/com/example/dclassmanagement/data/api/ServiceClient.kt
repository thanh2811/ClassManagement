package com.example.dclassmanagement.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceClient {

    var apiService: APIService? = null

    fun getService(): APIService {
        if (apiService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.100.6:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(APIService::class.java)
        }
        return apiService!!
    }

}