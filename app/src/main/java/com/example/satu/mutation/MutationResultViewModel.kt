package com.example.satu.mutation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.satu.mutation.network.Config
import com.example.satu.mutation.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MutationResultViewModel : ViewModel() {

    private val mutationHistory: MutableLiveData<List<MutationData>> = MutableLiveData()

    // Use a method to load mutation history with parameters
    fun loadMutationHistory(token: String, cardNumber: String, periodeMutasi: String, jenisTransaksi: String) {
        val call = RetrofitClient.apiService.getMutations(token, cardNumber, periodeMutasi, jenisTransaksi)
        call.enqueue(object : Callback<MutationResponse> {
            override fun onResponse(
                call: Call<MutationResponse>,
                response: Response<MutationResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    mutationHistory.value = response.body()?.data
                } else {
                    mutationHistory.value = emptyList()
                    // Optional: Log the error or notify the user
                }
            }

            override fun onFailure(call: Call<MutationResponse>, t: Throwable) {
                mutationHistory.value = emptyList()
                // Optional: Log the error or notify the user
            }
        })
    }

    fun getMutationHistory(): LiveData<List<MutationData>> {
        return mutationHistory
    }
}
