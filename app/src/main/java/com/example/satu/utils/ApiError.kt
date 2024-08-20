package com.example.satu.utils

import com.example.satu.data.model.response.ErrorResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

object ApiError{
    fun handleHttpException(exception: HttpException): Result.Error {
        val jsonInString = exception.response()?.errorBody()?.string()
        val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
        val errorMessage = errorBody.message
        return Result.Error(errorMessage)
    }
    fun handleHttpExceptionString(exception: HttpException): String {
        val jsonInString = exception.response()?.errorBody()?.string()
        return try {
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            errorBody.message ?: "Unknown error"
        } catch (e: JsonSyntaxException) {
            jsonInString ?: "Unknown error"
        }
    }
}