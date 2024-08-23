package com.example.satu.mutation.network

object Config {
    // Replace with your actual Bearer token
    private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6InRhdWZpcV9pbXJvbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE3MjQ0NTgwODYsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfUkVBRCIsIlJPTEVfV1JJVEUiXSwianRpIjoiWl9kTXB5QmdLbEZZTGQzTnFRWDBUX1NxU21ZIiwiY2xpZW50X2lkIjoibXktY2xpZW50LXdlYiJ9.qvUklQg8msn-QOwagO_Z74Knna-2Aj4wYAbwQut_16c"

    // Function to provide the Bearer token
    fun getBearerToken(): String {
        return "Bearer $BEARER_TOKEN"
    }
}