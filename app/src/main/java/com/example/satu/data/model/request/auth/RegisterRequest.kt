package com.example.satu.data.model.request.auth

data class RegisterRequest(
    val emailAddress: String,
    val password: String,
    val cardNumber: String,
    val phoneNumber: String,
    val pin: String
)