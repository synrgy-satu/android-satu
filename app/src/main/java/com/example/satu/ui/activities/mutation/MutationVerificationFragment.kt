package com.example.satu.ui.activities.mutation

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.satu.R
// import com.example.satu.ui.activities.mutation.network.Config
import com.example.satu.ui.activities.mutation.network.RetrofitClient
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class MutationVerificationFragment : Fragment() {

    private val passwordList = mutableListOf<Int>() // To keep track of input
    private var isPasswordVisible = false // To track visibility state

    private val correctPin = listOf(6,6,6,6,6,6) // Define your correct PIN here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mutation_verification, container, false)

        // Find the views
        val llEnterPassword = view.findViewById<LinearLayout>(R.id.llEnterPassword)
        val llNumberPin = view.findViewById<LinearLayout>(R.id.llNumberPin)
        val tvShowPassword = view.findViewById<TextView>(R.id.tvShowPassword)

        // Set the initial visibility of llNumberPin to GONE
        llNumberPin.visibility = View.GONE

        // Set an OnClickListener on llEnterPassword to change the visibility of llNumberPin and update tvShowPassword
        llEnterPassword.setOnClickListener {
            llNumberPin.visibility = View.VISIBLE

            // Update drawable for tvShowPassword
            updateShowPasswordIcon(tvShowPassword)
        }

        // Handle the Show Password button toggle
        tvShowPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            updatePasswordViews() // Update views based on visibility state
            updateShowPasswordIcon(tvShowPassword) // Update icon
        }

        // Number buttons
        val numberButtons = listOf(
            R.id.tvNumber1, R.id.tvNumber2, R.id.tvNumber3,
            R.id.tvNumber4, R.id.tvNumber5, R.id.tvNumber6,
            R.id.tvNumber7, R.id.tvNumber8, R.id.tvNumber9,
            R.id.tvNumber0
        )

        for (id in numberButtons) {
            view.findViewById<TextView>(id).setOnClickListener { v ->
                handleNumberClick(v as TextView)
            }
        }

        // Delete button
        view.findViewById<View>(R.id.ivDelete).setOnClickListener {
            handleDelete()
        }

        return view
    }

    private fun handleNumberClick(view: TextView) {
        val number = view.text.toString().toInt()
        val passwordListSize = passwordList.size

        if (passwordListSize < 6) {
            passwordList.add(number)
            updatePasswordViews()

            if (passwordListSize == 5) { // If all 6 digits are entered
               // checkPin()
                validatePin(passwordList.joinToString(""))
            }
        }
    }

    private fun handleDelete() {
        if (passwordList.isNotEmpty()) {
            passwordList.removeAt(passwordList.size - 1)
            updatePasswordViews()
        }
    }

    private fun updatePasswordViews() {
        val drawableFilled = ContextCompat.getDrawable(requireContext(), R.drawable.ic_circle)
        val drawableEmpty = ContextCompat.getDrawable(requireContext(), R.drawable.ic_min)

        val passwordViews = listOf(
            R.id.tvPassword1, R.id.tvPassword2, R.id.tvPassword3,
            R.id.tvPassword4, R.id.tvPassword5, R.id.tvPassword6
        ).map { id -> view?.findViewById<TextView>(id) }

        passwordViews.forEachIndexed { index, textView ->
            textView?.let {
                if (index < passwordList.size) {
                    if (isPasswordVisible) {
                        it.text = passwordList[index].toString() // Show the number
                        it.setPadding(0, 25, 0, 0)
                        it.setTypeface(null, Typeface.BOLD)
                        it.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    } else {
                        it.text = "" // Hide the number
                        it.setPadding(0, 0, 0, 35)
                        it.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableFilled)
                    }
                } else {
                    it.text = ""
                    it.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableEmpty)
                }
            }
        }
    }

    private fun updateShowPasswordIcon(tvShowPassword: TextView) {
        val drawableStart = if (isPasswordVisible) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye) // Eye closed icon
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye_slash) // Eye open icon
        }
        tvShowPassword.setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)
    }

    private fun checkPin() {
        if (passwordList == correctPin) {
            val mutationResultFragment = MutationResultFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mutationResultFragment) // Use your container ID
                .addToBackStack(null) // Optional: add to back stack if you want to allow navigation back
                .commit()
        } else {
            // Optionally, show an error message or clear the input for retry
            passwordList.clear()
            updatePasswordViews()
        }
    }

    // Extension function to convert dp to px
    private fun Int.dpToPx(): Float {
        return this * resources.displayMetrics.density
    }

    private fun validatePin(pin: String) {
        val sharedPrefToken = requireActivity().getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        val tokens = sharedPrefToken .getString("token", "")
        val token = "Bearer $tokens"
        // val token = Config.getBearerToken() // Your token
        val body: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())

        val call = RetrofitClient.apiService.validatePin(token, pin, body)
        call.enqueue(object : retrofit2.Callback<PinValidationResponse> {
            override fun onResponse(
                call: Call<PinValidationResponse>,
                response: Response<PinValidationResponse>
            ) {
                if (response.isSuccessful && response.body()?.status == true) {
                    // PIN is valid, navigate to MutationResultFragment
                    val mutationResultFragment = MutationResultFragment()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, mutationResultFragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    // Show an error message
                    Toast.makeText(context, "PIN tidak valid", Toast.LENGTH_SHORT).show()
                    passwordList.clear()
                    updatePasswordViews()
                }
            }

            override fun onFailure(call: Call<PinValidationResponse>, t: Throwable) {
                // Handle error
                Toast.makeText(context, "Failed to validate PIN: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
