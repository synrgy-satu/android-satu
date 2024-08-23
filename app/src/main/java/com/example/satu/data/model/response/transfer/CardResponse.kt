package com.example.satu.data.model.response.transfer

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CardResponse(
	val code: Int? = null,
	val data: DataCardRekening? = null,
	val message: String? = null,
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DataCardRekening(
	val name: String? = null,
	val rekeningNumber: Long? = null
) : Parcelable
