package com.example.satu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.satu.data.repository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(emailAddress: String, password: String, cardNumber:  String, phoneNumber:  String, pin: String) =
        userRepository.register(emailAddress, password, cardNumber, phoneNumber, pin)
}