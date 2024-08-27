package com.example.satu.data.repository

import android.app.Application
import androidx.lifecycle.liveData
import com.example.satu.R
import com.example.satu.api.ApiService
import com.example.satu.data.model.request.transfer.TransferRequest
import com.example.satu.utils.ApiError.handleHttpException
import retrofit2.HttpException
import java.io.IOException

class TransferRepository private constructor(
    private val apiService: ApiService,
    private val application: Application
) {
    fun getCardRekening(rekeningNumber: Long) = liveData {
        emit(com.example.common.Result.Loading)
        try {
            val response = apiService.getCardRekening(rekeningNumber)
            emit(com.example.common.Result.Success(response))
        }catch (e: HttpException) {
            emit(handleHttpException(e))
        } catch (exception: IOException) {
            emit(com.example.common.Result.Error(application.resources.getString(R.string.network_error_message)))
        } catch (exception: Exception) {
            emit(com.example.common.Result.Error(exception.message ?: application.resources.getString(R.string.unknown_error)))
        }
    }

    fun addTransfer(token: String, debitedRekeningNumber: Long, targetRekeningNumber: Long, amount: Long, pin: String, note: String) = liveData {
        emit(com.example.common.Result.Loading)
        try {
            val response = apiService.addTransfer("Bearer $token", TransferRequest(debitedRekeningNumber, targetRekeningNumber, amount, pin, note))
            emit(com.example.common.Result.Success(response))
        }catch (e: HttpException) {
            emit(handleHttpException(e))
        } catch (exception: IOException) {
            emit(com.example.common.Result.Error(application.resources.getString(R.string.network_error_message)))
        } catch (exception: Exception) {
            emit(com.example.common.Result.Error(exception.message ?: application.resources.getString(R.string.unknown_error)))
        }
    }

    companion object {
        @Volatile
        private var instance: TransferRepository? = null
        fun getInstance(
            apiService: ApiService,
            application: Application
        ): TransferRepository =
            instance ?: synchronized(this) {
                instance ?: TransferRepository(apiService, application)
            }.also { instance = it }
    }
}