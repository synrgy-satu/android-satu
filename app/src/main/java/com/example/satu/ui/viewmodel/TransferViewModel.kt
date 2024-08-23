package com.example.satu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.satu.data.repository.TransferRepository

class TransferViewModel(private val transferRepository: TransferRepository) : ViewModel() {
    fun getCardRekening(rekeningNumber: Long) = transferRepository.getCardRekening(rekeningNumber)

}