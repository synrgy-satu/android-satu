package com.example.satu.data.model.response
import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("data")
    val data: Any? = null,

    @field:SerializedName("status")
    val stauts: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("code")
    val code: Int
)