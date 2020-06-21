package com.application.assignmentkotlin.network

import com.application.assignmentkotlin.db.TaskData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiCallInterface {



    @Headers("Content-Type:application/json")
    @GET("repositories")
    fun  getItemsDetails(): Call<List<TaskData>>
}