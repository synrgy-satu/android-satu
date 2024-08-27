package com.example.satu.data.repository

import android.app.Application
import com.example.satu.api.ApiService
import com.example.satu.data.local.UserPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.satu.R
import com.example.satu.data.model.request.auth.CardCheckRequest
import com.example.satu.data.model.request.auth.LoginRequest
import com.example.satu.data.model.request.auth.RegisterRequest
import com.example.satu.data.model.response.auth.DataUser
import com.example.satu.utils.ApiError
import com.example.satu.utils.ApiError.handleHttpException
import retrofit2.HttpException
import java.io.IOException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val application: Application,
    private val userPref: UserPreferences
) {
    private suspend fun <T> apiCall(call: suspend () -> T): com.example.common.Result<T> = try {
        com.example.common.Result.Success(call())
    } catch (e: HttpException) {
        com.example.common.Result.Error(ApiError.handleHttpExceptionString(e))
    } catch (exception: IOException) {
        com.example.common.Result.Error(application.resources.getString(R.string.network_error_message))
    } catch (exception: Exception) {
        com.example.common.Result.Error(exception.message ?: application.resources.getString(R.string.unknown_error))
    }

    fun register(emailAddress: String, password: String, cardNumber:  Long, phoneNumber:  String, pin: String) = liveData {
        emit(com.example.common.Result.Loading)
        try {
            val response = apiService.register(RegisterRequest(emailAddress, password, cardNumber, phoneNumber, pin))
            emit(com.example.common.Result.Success(response))
        }catch (e: HttpException) {
            emit(handleHttpException(e))
        } catch (exception: IOException) {
            emit(com.example.common.Result.Error(application.resources.getString(R.string.network_error_message)))
        } catch (exception: Exception) {
            emit(com.example.common.Result.Error(exception.message ?: application.resources.getString(R.string.unknown_error)))
        }
    }

    fun cardCheck(cardNumber: Long, month: Int, year: Int) = liveData {
        emit(com.example.common.Result.Loading)
        try {
            val response = apiService.cardCheck(CardCheckRequest(cardNumber, month, year))
            emit(com.example.common.Result.Success(response))
        }catch (e: HttpException) {
            emit(handleHttpException(e))
        } catch (exception: IOException) {
            emit(com.example.common.Result.Error(application.resources.getString(R.string.network_error_message)))
        } catch (exception: Exception) {
            emit(com.example.common.Result.Error(exception.message ?: application.resources.getString(R.string.unknown_error)))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(com.example.common.Result.Loading)
        emit(apiCall {
            val response = apiService.login(LoginRequest(email, password))
            response.data?.let { saveSession(it) }
            response
        })
    }


    suspend fun saveSession(data: DataUser) = userPref.saveSession(data)
    fun getSession(): LiveData<DataUser> = userPref.getSession().asLiveData()
    suspend fun deleteSession() {
        userPref.deleteSession()
    }

    fun getUser(token: String) = liveData {
        emit(com.example.common.Result.Loading)
        try {
            val response = apiService.getUser("Bearer $token")
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
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            application: Application,
            pref: UserPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, application, pref)
            }.also { instance = it }
    }
}