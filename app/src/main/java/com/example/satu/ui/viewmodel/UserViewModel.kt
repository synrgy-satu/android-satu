package com.example.satu.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satu.data.model.response.auth.DataUser
import com.example.satu.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUser(token: String) = userRepository.getUser(token)
    fun getToken(): LiveData<DataUser> =  userRepository.getSession()

}