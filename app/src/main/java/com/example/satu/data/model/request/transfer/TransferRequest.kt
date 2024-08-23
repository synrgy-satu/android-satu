package com.example.satu.data.model.request.transfer

data class TransferRequest(
    val debitedRekeningNumber: Long,
    val targetRekeningNumber: Long,
    val amount: Long,
    val pin: String,
    val note: String,
)