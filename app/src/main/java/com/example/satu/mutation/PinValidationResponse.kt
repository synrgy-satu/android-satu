package com.example.satu.mutation

data class PinValidationResponse(
    val code: Int,
    val message: String,
    val status: Boolean,
    val data: Any? // Adjust the type based on the actual response structure
)
