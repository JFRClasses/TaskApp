package com.example.taskapp.datasource.services

import com.example.taskapp.domain.dtos.TaskResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TaskService {
    @GET("tasks")
    suspend fun getTasks(@Query("userId") userId : Int) : Response<TaskResponse>
}