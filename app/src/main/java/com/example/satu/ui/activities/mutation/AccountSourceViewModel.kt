package com.example.satu.ui.activities.mutation
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
// import com.example.satu.ui.activities.mutation.network.Config
import com.example.satu.ui.activities.mutation.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class AccountSourceViewModel(application: Application) : AndroidViewModel(application) {

    private val _accounts = MutableLiveData<List<AccountSource>>()
    val accounts: LiveData<List<AccountSource>> get() = _accounts

    init {
        fetchAccountSources()
    }

    private fun fetchAccountSources() {
        // val token = Config.getBearerToken() // diganti shared rpeferences?
        val token = "Bearer ${getTokenFromSharedPreferences()}"
        RetrofitClient.apiService.getAccountSources(token).enqueue(object :
            Callback<ApiResponse<List<AccountSourceResponse>>> {
            override fun onResponse(
                call: Call<ApiResponse<List<AccountSourceResponse>>>,
                response: Response<ApiResponse<List<AccountSourceResponse>>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { data ->
                        _accounts.value = data.map { it.toAccountSource() }
                    }
                } else {
                    // Handle non-successful response
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<AccountSourceResponse>>>, t: Throwable) {
                // Handle error
                _accounts.postValue(emptyList())  // Clear the data or handle it as needed
                t.localizedMessage?.let { message ->
                    context?.let {
                        // Format the current date and time
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        val dateTimeNow = dateFormat.format(System.currentTimeMillis())

                        // Create the Toast message with date and time
                        val toastMessage = "Error: $message\nOccurred at: $dateTimeNow"
                        Toast.makeText(it, toastMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun getTokenFromSharedPreferences(): String? {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    // You might need a reference to a Context, pass it via the constructor or another method
    private var context: Context? = null
    fun setContext(context: Context) {
        this.context = context
    }
}
