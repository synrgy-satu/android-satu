package com.example.satu.api

import com.example.satu.data.model.request.auth.CardCheckRequest
import com.example.satu.data.model.request.auth.ForgotPasswordRequest
import com.example.satu.data.model.request.auth.LoginRequest
import com.example.satu.data.model.request.auth.RegisterRequest
import com.example.satu.data.model.request.transfer.TransferRequest
import com.example.satu.data.model.response.auth.CardCheckResponse
import com.example.satu.data.model.response.auth.ForgotPasswordResponse
import com.example.satu.data.model.response.auth.LoginResponse
import com.example.satu.data.model.response.auth.RegisterResponse
import com.example.satu.data.model.response.transfer.CardResponse
import com.example.satu.data.model.response.transfer.TransferResponse
import com.example.satu.data.model.response.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("auth")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): UserResponse

    @GET("card/{rekeningNumber}")
    suspend fun getCardRekening(
        @Path("rekeningNumber") rekeningNUmber: Long
    ): CardResponse

    @Headers("Content-Type: application/json")
    @POST("action/transfer")
    suspend fun addTransfer(
        @Header("Authorization") token: String,
        @Body request: TransferRequest): TransferResponse

    @Headers("Content-Type: application/json")
    @POST("auth/password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse

}