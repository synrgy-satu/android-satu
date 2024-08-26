package com.example.satu.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.satu.data.repository.QrisRepository
import com.example.satu.data.repository.TransferRepository
import com.example.satu.di.Injection
import com.example.satu.ui.viewmodel.QrisViewModel
import com.example.satu.ui.viewmodel.TransferViewModel

class QrisViewModelfactory private constructor(private val qrisRepository: QrisRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>):  T =
        when {
            modelClass.isAssignableFrom(QrisViewModel::class.java) ->
               QrisViewModel(qrisRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    companion object {
        @Volatile
        private var instance: QrisViewModelfactory? = null
        fun getInstance(application: Application): QrisViewModelfactory =
            instance ?: synchronized(this) {
                instance ?: QrisViewModelfactory(Injection.provideQrisRepository(application))
            }.also { instance = it }
    }
}