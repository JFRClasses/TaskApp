package com.example.taskapp.datasource.services

import com.example.taskapp.domain.dtos.Auth
import com.example.taskapp.domain.dtos.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body auth: Auth) : Response<AuthResponse>

    @POST("user")
    suspend fun createUser(@Body auth: Auth) : Response<AuthResponse>
}