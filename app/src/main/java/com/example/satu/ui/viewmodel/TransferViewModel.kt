package com.example.satu.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satu.data.model.response.auth.DataUser
import com.example.satu.data.repository.TransferRepository
import com.example.satu.data.repository.UserRepository
import kotlinx.coroutines.launch

class TransferViewModel(private val transferRepository: TransferRepository) : ViewModel() {
    fun getCardRekening(rekeningNumber: Long) = transferRepository.getCardRekening(rekeningNumber)

}