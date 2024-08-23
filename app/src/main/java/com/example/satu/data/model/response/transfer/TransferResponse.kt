package com.example.satu.data.model.response.transfer

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class TransferResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataTransfer? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataTransfer(

	@field:SerializedName("reason")
	val reason: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("balanceHistory")
	val balanceHistory: Int? = null,

	@field:SerializedName("referenceNumber")
	val referenceNumber: String? = null,

	@field:SerializedName("created_date")
	val createdDate: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("vendors")
	val vendors: Vendors? = null,

	@field:SerializedName("jenisTransaksi")
	val jenisTransaksi: String? = null
) : Parcelable

@Parcelize
data class Vendors(

	@field:SerializedName("created_date")
	val createdDate: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("vendorName")
	val vendorName: String? = null
) : Parcelable
