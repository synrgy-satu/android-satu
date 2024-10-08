package com.example.satu.ui.activities.mutation.network


import com.example.satu.ui.activities.mutation.AccountSourceResponse
import com.example.satu.ui.activities.mutation.ApiResponse
import com.example.satu.ui.activities.mutation.MutationResponse
import com.example.satu.ui.activities.mutation.PinValidationResponse
import com.example.satu.ui.activities.mutation.UserDataResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("mutasi/sumber-rekening")
    fun getAccountSources(
        @Header("Authorization") token: String
    ): Call<ApiResponse<List<AccountSourceResponse>>>

    @POST("mutasi/pin")
    fun validatePin(
        @Header("Authorization") token: String,
        @Query("pin") pin: String,
        @Body body: RequestBody // You can pass an empty body if necessary
    ): Call<PinValidationResponse>

    @GET("auth")
    fun getUserData(
        @Header("Authorization") token: String
    ): Call<UserDataResponse>

    // Add your mutation API call here
    @GET("mutasi/mobile")
    fun getMutations(
        @Header("Authorization") token: String,
        @Query("cardNumber") cardNumber: String,
        @Query("jenisTransaksi") jenisTransaksi: String,
        @Query("tanggalMulai") tanggalMulai: String,
        @Query("tanggalSelesai") tanggalSelesai: String
    ): Call<MutationResponse>

    @GET("mutasi/mobile")
    fun getMutationsOld(
        @Header("Authorization") token: String,
        @Query("cardNumber") cardNumber: String,
        @Query("periodeMutasi") periodeMutasi: String,
        @Query("jenisTransaksi") jenisTransaksi: String
    ): Call<MutationResponse>

}