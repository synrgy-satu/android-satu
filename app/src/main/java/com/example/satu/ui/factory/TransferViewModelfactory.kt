package com.example.satu.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.satu.data.repository.TransferRepository
import com.example.satu.data.repository.UserRepository
import com.example.satu.di.Injection
import com.example.satu.ui.viewmodel.LoginViewModel
import com.example.satu.ui.viewmodel.RegisterViewModel
import com.example.satu.ui.viewmodel.TransferViewModel
import com.example.satu.ui.viewmodel.UserViewModel

class TransferViewModelfactory private constructor(private val transferRepository: TransferRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>):  T =
        when {
            modelClass.isAssignableFrom(TransferViewModel::class.java) ->
                TransferViewModel(transferRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    companion object {
        @Volatile
        private var instance: TransferViewModelfactory? = null
        fun getInstance(application: Application): TransferViewModelfactory =
            instance ?: synchronized(this) {
                instance ?: TransferViewModelfactory(Injection.provideTransferRepository(application))
            }.also { instance = it }
    }
}