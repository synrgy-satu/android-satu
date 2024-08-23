package com.example.satu.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.satu.R
import com.example.satu.mutation.network.Config
import com.example.satu.mutation.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    // Add other views as needed

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize your views
        usernameTextView = view.findViewById(R.id.tvAccountName)
        emailTextView = view.findViewById(R.id.tvAccountEmail)
        // Initialize other views here

        // Fetch user data
        fetchUser()

        return view
    }

    private fun fetchUser() {
        val token = Config.getBearerToken() // Your token
        val call = RetrofitClient.apiService.getUserData(token)

        call.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(
                call: Call<UserDataResponse>,
                response: Response<UserDataResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val userData = response.body()!!
                    // Update the UI with the fetched user data
                    usernameTextView.text = userData.data.fullName
                    emailTextView.text = userData.data.emailAddress
                    // Update other views here
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(context, "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                // Handle error
                Toast.makeText(context, "Failed to fetch user data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}