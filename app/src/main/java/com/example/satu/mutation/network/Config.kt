package com.example.satu.mutation.network

object Config {
    // Replace with your actual Bearer token
    private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6InRhdWZpcV9pbXJvbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE3MjQ1NDgwNzUsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfUkVBRCIsIlJPTEVfV1JJVEUiXSwianRpIjoiUHUzUkdrMWd2bzA0MFBZWldHM3gzRFhqaEN3IiwiY2xpZW50X2lkIjoibXktY2xpZW50LXdlYiJ9.3uHI_DwOHCcEkOPuhBbyj-2ufgDHjEXAAS-9Ut8XGfM"

    // Function to provide the Bearer token
    fun getBearerToken(): String {
        return "Bearer $BEARER_TOKEN"
    }
}