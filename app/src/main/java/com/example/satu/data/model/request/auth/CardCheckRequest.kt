package com.example.satu.data.model.request.auth

data class CardCheckRequest(
    val cardNumber: Long,
    val month: Int,
    val year: Int
)