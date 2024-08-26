package com.example.satu.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.satu.R
import com.example.satu.api.ApiService
import com.example.satu.data.model.request.auth.CardCheckRequest
import com.example.satu.data.model.request.transfer.TransferRequest
import com.example.satu.utils.ApiError
import com.example.satu.utils.ApiError.handleHttpException
import com.example.satu.utils.Result
import retrofit2.HttpException
import java.io.IOException

class QrisRepository private constructor(
    private val apiService: ApiService,
    private val application: Application
) {
    fun getDataQris(token: String, qrOrId: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDataQris(token, qrOrId)
            emit(Result.Success(response))
        }catch (e: HttpException) {
            emit(handleHttpException(e))
        } catch (exception: IOException) {
            emit(Result.Error(application.resources.getString(R.string.network_error_message)))
        } catch (exception: Exception) {
            emit(Result.Error(exception.message ?: application.resources.getString(R.string.unknown_error)))
        }
    }

    companion object {
        @Volatile
        private var instance: QrisRepository? = null
        fun getInstance(
            apiService: ApiService,
            application: Application
        ): QrisRepository =
            instance ?: synchronized(this) {
                instance ?: QrisRepository(apiService, application)
            }.also { instance = it }
    }
}