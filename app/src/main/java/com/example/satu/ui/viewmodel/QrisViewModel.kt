package com.example.satu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.satu.data.repository.QrisRepository
import com.example.satu.data.repository.TransferRepository

class QrisViewModel(private val qrisRepository: QrisRepository) : ViewModel() {
    fun getDataQris(token: String, qr: String) = qrisRepository.getDataQris(token, qr)
    fun addQris(
        token: String,
        debitedRekeningNumber: Long,
        targetQris: String,
        amount: Long,
        pin: String,
    ) = qrisRepository.addQris(
        token,
        debitedRekeningNumber,
        targetQris,
        amount,
        pin
    )


}