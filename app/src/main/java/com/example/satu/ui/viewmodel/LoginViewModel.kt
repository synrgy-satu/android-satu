package com.example.satu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satu.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun login(email: String, password: String) = userRepository.login(email, password)
    fun getUserLogin() = userRepository.getSession()
    suspend fun deleteUserLogin() {
        userRepository.deleteSession()
    }
}