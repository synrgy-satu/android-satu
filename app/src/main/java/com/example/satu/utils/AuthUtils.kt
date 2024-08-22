package com.example.satu.utils

class AuthUtils {
    companion object {
        fun getAuthToken(token: String) = "Bearer $token"
    }
}