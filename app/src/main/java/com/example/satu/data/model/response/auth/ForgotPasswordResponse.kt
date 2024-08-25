package com.example.satu.data.model.response.auth

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ForgotPasswordResponse(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("data")
    val data: String? = null
) : Parcelable
