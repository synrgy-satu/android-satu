package com.example.satu.mutation

data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val status: Boolean,
    val data: T
)

data class AccountSourceResponse(
    val fullName: String?,
    val cardNumber: String,
    val jenisRekening: String
)

fun AccountSourceResponse.toAccountSource(): AccountSource {
    return AccountSource(
        accountNumber = cardNumber,
        accountName = fullName ?: "Unknown",
        accountSource = jenisRekening
    )
}
