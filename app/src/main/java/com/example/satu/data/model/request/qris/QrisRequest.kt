package com.example.satu.data.model.request.qris

data class QrisRequest(
    val debitedRekeningNumber: Long,
    val targetQris: String,
    val amount: Long,
    val pin: String,
)