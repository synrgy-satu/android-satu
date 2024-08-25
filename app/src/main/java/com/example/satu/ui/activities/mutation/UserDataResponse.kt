package com.example.satu.ui.activities.mutation

data class UserDataResponse(
    val code: Int,
    val message: String,
    val status: Boolean,
    val data: UserData
)

data class UserData(
    val created_date: String,
    val id: String,
    val username: String,
    val fullName: String,
    val emailAddress: String,
    val pin: String,
    val phoneNumber: String,
    val rekenings: List<Rekening>
)

data class Rekening(
    val created_date: String,
    val id: String,
    val cardNumber: Long,
    val rekeningNumber: Long,
    val name: String,
    val jenisRekening: String,
    val expiredDateMonth: Int,
    val expiredDateYear: Int,
    val balance: Int
)
