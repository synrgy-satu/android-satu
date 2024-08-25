package com.example.satu.mutation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.satu.mutation.network.Config
import com.example.satu.mutation.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class MutationResultViewModel : ViewModel() {

    private val mutationHistory: MutableLiveData<List<MutationData>> = MutableLiveData()

    fun loadMutationHistory(token: String, cardNumber: String, periodeMutasi: String, jenisTransaksi: String) {
        if (isDateRangeFormat(periodeMutasi)) {
            val (startDate, endDate) = parseDateRange(periodeMutasi)
            loadMutationsNew(token, cardNumber, startDate, endDate, jenisTransaksi)
        } else {
            loadMutationsOld(token, cardNumber, periodeMutasi, jenisTransaksi)
        }
    }

    private fun isDateRangeFormat(periodeMutasi: String): Boolean {
        return periodeMutasi.contains(" - ")
    }

    private fun parseDateRange(periodeMutasi: String): Pair<String, String> {
        val parts = periodeMutasi.split(" - ")
        val startDate = convertToApiFormat(parts[0])
        val endDate = convertToApiFormat(parts[1])
        return Pair(startDate, endDate)
    }

    // Function to convert a date to API format (in this case, keeping it in "dd MMMM yyyy")
    private fun convertToApiFormat(date: String): String {
        val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) // Same format
        val parsedDate = inputFormat.parse(date)
        return outputFormat.format(parsedDate)
    }


    private fun loadMutationsNew(token: String, cardNumber: String, startDate: String, endDate: String, jenisTransaksi: String) {
        val call = RetrofitClient.apiService.getMutations(token, cardNumber, jenisTransaksi, startDate, endDate)
        call.enqueue(object : Callback<MutationResponse> {
            override fun onResponse(
                call: Call<MutationResponse>,
                response: Response<MutationResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    mutationHistory.value = response.body()?.data
                } else {
                    mutationHistory.value = emptyList()
                }
            }

            override fun onFailure(call: Call<MutationResponse>, t: Throwable) {
                mutationHistory.value = emptyList()
            }
        })
    }

    private fun loadMutationsOld(token: String, cardNumber: String, periodeMutasi: String, jenisTransaksi: String) {
        val call = RetrofitClient.apiService.getMutationsOld(token, cardNumber, periodeMutasi, jenisTransaksi)
        call.enqueue(object : Callback<MutationResponse> {
            override fun onResponse(
                call: Call<MutationResponse>,
                response: Response<MutationResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    mutationHistory.value = response.body()?.data
                } else {
                    mutationHistory.value = emptyList()
                }
            }

            override fun onFailure(call: Call<MutationResponse>, t: Throwable) {
                mutationHistory.value = emptyList()
            }
        })
    }

    fun getMutationHistory(): LiveData<List<MutationData>> {
        return mutationHistory
    }
}

