package com.example.satu.di

import android.app.Application
import com.example.satu.api.ApiConfig
import com.example.satu.data.local.UserPreferences
import com.example.satu.data.local.dataStore
import com.example.satu.data.repository.UserRepository

object Injection {
    private fun provideApiService() = ApiConfig.getApiService()
    private fun provideUserPreferences(application: Application) =
        UserPreferences.getInstance(application.dataStore)
    fun provideUserRepository(application: Application) =
        UserRepository.getInstance(provideApiService(), application, provideUserPreferences(application))

}