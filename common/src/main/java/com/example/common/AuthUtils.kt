package com.example.common

class AuthUtils {
    companion object {
        fun getAuthToken(token: String) = "Bearer $token"
    }
}