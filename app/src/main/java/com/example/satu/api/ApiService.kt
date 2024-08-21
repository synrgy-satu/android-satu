package com.example.satu.api

import com.example.satu.data.model.request.auth.CardCheckRequest
import com.example.satu.data.model.request.auth.LoginRequest
import com.example.satu.data.model.request.auth.RegisterRequest
import com.example.satu.data.model.response.auth.CardCheckResponse
import com.example.satu.data.model.response.auth.LoginResponse
import com.example.satu.data.model.response.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @Headers("Content-Type: application/json")
    @POST("card/check")
    suspend fun cardCheck(@Body request: CardCheckRequest): CardCheckResponse

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}