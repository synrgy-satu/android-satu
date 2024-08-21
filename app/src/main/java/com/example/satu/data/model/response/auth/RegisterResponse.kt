package com.example.satu.data.model.response.auth

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataRegister? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class RekeningsItem(

	@field:SerializedName("expiredDateMonth")
	val expiredDateMonth: Int? = null,

	@field:SerializedName("balance")
	val balance: Int? = null,

	@field:SerializedName("jenisRekening")
	val jenisRekening: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_date")
	val createdDate: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("rekeningNumber")
	val rekeningNumber: Long? = null,

	@field:SerializedName("expiredDateYear")
	val expiredDateYear: Int? = null,

	@field:SerializedName("cardNumber")
	val cardNumber: Long? = null
) : Parcelable

@Parcelize
data class DataRegister(

	@field:SerializedName("rekenings")
	val rekenings: List<RekeningsItem?>? = null,

	@field:SerializedName("emailAddress")
	val emailAddress: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("pin")
	val pin: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("created_date")
	val createdDate: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
