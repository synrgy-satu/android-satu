package com.example.satu.mutation.network

object Config {
    // Replace with your actual Bearer token
    private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6InRhdWZpcV9pbXJvbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE3MjQ0MTQ5ODEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfUkVBRCIsIlJPTEVfV1JJVEUiXSwianRpIjoiY2xSTnlrdWJTLTlUOWY1U0tVVkQ1Z0NmMjQ4IiwiY2xpZW50X2lkIjoibXktY2xpZW50LXdlYiJ9.kH2qlibDxeGFAIMvHz57AzXGjaM4lO4XuHbe1HlE3g0"

    // Function to provide the Bearer token
    fun getBearerToken(): String {
        return "Bearer $BEARER_TOKEN"
    }
}