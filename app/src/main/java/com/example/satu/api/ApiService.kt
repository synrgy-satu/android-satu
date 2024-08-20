package com.example.satu.api

import com.example.satu.data.model.request.auth.RegisterRequest
import com.example.satu.data.model.response.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}