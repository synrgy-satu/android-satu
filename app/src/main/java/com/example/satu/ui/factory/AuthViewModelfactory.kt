package com.example.satu.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.satu.data.repository.UserRepository
import com.example.satu.di.Injection
import com.example.satu.ui.viewmodel.LoginViewModel
import com.example.satu.ui.viewmodel.RegisterViewModel
import com.example.satu.ui.viewmodel.UserViewModel

class AuthViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>):  T =
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) ->
                UserViewModel(userRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    companion object {
        @Volatile
        private var instance: AuthViewModelFactory? = null
        fun getInstance(application: Application): AuthViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AuthViewModelFactory(Injection.provideUserRepository(application))
            }.also { instance = it }
    }
}