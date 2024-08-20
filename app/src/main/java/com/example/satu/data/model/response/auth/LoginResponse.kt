package com.example.satu.data.model.response.auth

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LoginResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataUser? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataUser(

	@field:SerializedName("expiresIn")
	val expiresIn: Int? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("tokenType")
	val tokenType: String? = null,

	@field:SerializedName("jti")
	val jti: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String
) : Parcelable
