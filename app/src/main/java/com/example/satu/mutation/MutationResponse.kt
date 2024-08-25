package com.example.satu.mutation

data class MutationResponse(
    val code: Int,
    val message: String,
    val status: Boolean,
    val data: List<MutationData>
)

data class MutationData(
    val username: String,
    val cardNumber: Long,
    val jenisRekening: String,
    val periodeMutasi: String,
    val balance: Long,
    val createdDate: String,
    val amount: Long,
    val referenceNumber: String,
    val note: String?,
    val vendorCode: String?,
    val vendorName: String?,
    val jenisTransaksi: String
)