package com.example.satu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.satu.data.repository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(emailAddress: String = "dummy@gmail.com", password: String = "123456", cardNumber:  String, phoneNumber:  String = "081239812398", pin: String ="123456") =
        userRepository.register(emailAddress, password, cardNumber, phoneNumber, pin)
}