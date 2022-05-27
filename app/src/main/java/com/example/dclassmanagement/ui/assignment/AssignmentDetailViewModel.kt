package com.example.dclassmanagement.ui.assignment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dclassmanagement.data.api.ServiceClient
import com.example.dclassmanagement.data.model.MarkRequest
import com.example.dclassmanagement.data.model.MarkResponse
import retrofit2.Call
import retrofit2.Response

class AssignmentDetailViewModel: ViewModel() {

    private var _agmResult = MutableLiveData<MarkResponse>()
    var agmResult = _agmResult as LiveData<MarkResponse>

    fun getScore(markRequest: MarkRequest){
        ServiceClient.getService().getScore(markRequest).enqueue(
            object : retrofit2.Callback<MarkResponse>{
                override fun onResponse(
                    call: Call<MarkResponse>,
                    response: Response<MarkResponse>
                ) {
                    if(response.code() == 200){
                        Log.d("res", response.toString())
                        _agmResult.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MarkResponse>, t: Throwable) {
                    Log.d("error", t.toString())
                }

            }
        )
    }

}