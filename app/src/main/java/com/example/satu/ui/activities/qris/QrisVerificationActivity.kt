package com.example.satu.ui.activities.qris

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.satu.R
import com.example.satu.data.model.response.qris.DataQrisNih
import com.example.satu.databinding.ActivityQrisVerificationBinding
import com.example.satu.ui.activities.mutation.PinValidationResponse
import com.example.satu.ui.activities.mutation.network.RetrofitClient
import com.example.satu.ui.factory.QrisViewModelfactory
import com.example.satu.ui.viewmodel.QrisViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.SnackbarUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class QrisVerificationActivity : AppCompatActivity() {

    private val passwordList = mutableListOf<Int>() // To keep track of input
    private var isPasswordVisible = false // To track visibility state

    private val viewModel: QrisViewModel by viewModels {
        QrisViewModelfactory.getInstance(application)
    }

    private val correctPin = listOf(6, 6, 6, 6, 6, 6) // Define your correct PIN here
    private lateinit var binding: ActivityQrisVerificationBinding
    private lateinit var amount: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qris_verification)
        binding = ActivityQrisVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Find the views
        val llEnterPassword = findViewById<LinearLayout>(R.id.llEnterPassword)
        val llNumberPin = findViewById<LinearLayout>(R.id.llNumberPin)
        val tvShowPassword = findViewById<TextView>(R.id.tvShowPassword)

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
            findViewById<TextView>(id).setOnClickListener { v ->
                handleNumberClick(v as TextView)
            }
        }

        // Delete button
        findViewById<View>(R.id.ivDelete).setOnClickListener {
            handleDelete()
        }
    }

    private fun handleNumberClick(view: TextView) {
        val number = view.text.toString().toInt()
        val passwordListSize = passwordList.size

        if (passwordListSize < 6) {
            passwordList.add(number)
            updatePasswordViews()

            if (passwordListSize == 5) { // If all 6 digits are entered
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
        val drawableFilled = ContextCompat.getDrawable(this, R.drawable.ic_circle)
        val drawableEmpty = ContextCompat.getDrawable(this, R.drawable.ic_min)

        val passwordViews = listOf(
            R.id.tvPassword1, R.id.tvPassword2, R.id.tvPassword3,
            R.id.tvPassword4, R.id.tvPassword5, R.id.tvPassword6
        ).map { id -> findViewById<TextView>(id) }

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
            ContextCompat.getDrawable(this, R.drawable.ic_eye) // Eye closed icon
        } else {
            ContextCompat.getDrawable(this, R.drawable.ic_eye_slash) // Eye open icon
        }
        tvShowPassword.setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)
    }

    private fun validatePin(pin: String) {
        val sharedPrefToken = getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        val tokens = sharedPrefToken.getString("token", "")
        val token = "Bearer $tokens"
        val body: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())

        val call = RetrofitClient.apiService.validatePin(token, pin, body)
        call.enqueue(object : retrofit2.Callback<PinValidationResponse> {
            override fun onResponse(
                call: Call<PinValidationResponse>,
                response: Response<PinValidationResponse>
            ) {
                if (response.isSuccessful && response.body()?.status == true) {
                    // PIN is valid, add data

                    amount = intent.getStringExtra("amount").toString()
                    val targetQris = intent.getStringExtra("targetQris")
                    val debitedRekeningNumber = intent.getStringExtra("debitedRekeningNumber")

                    val sharedPrefToken = getSharedPreferences("UserToken", Context.MODE_PRIVATE)
                    val token = sharedPrefToken .getString("token", "")

                    if (debitedRekeningNumber != null) {
                        if (amount != null) {
                            amount.toLongOrNull()?.let {
                                debitedRekeningNumber.toLongOrNull()?.let { it1 ->
                                    if (token != null) {
                                        if (targetQris != null) {
                                            viewModel.addQris(token, it1, targetQris,
                                                it, pin).observe(this@QrisVerificationActivity) { result ->
                                                when (result) {
                                                    is com.example.common.Result.Loading -> ProgressDialogUtils.showProgressDialog(this@QrisVerificationActivity)
                                                    is com.example.common.Result.Success -> result.data.data?.let { onLoginSuccess(it) }
                                                    is com.example.common.Result.Error -> onError(result.error)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    val intent = Intent(this@QrisVerificationActivity, QrisReceiptActivity::class.java)
//                    startActivity(intent)
                } else {
                    // Show an error message
                    Toast.makeText(this@QrisVerificationActivity, "PIN tidak valid", Toast.LENGTH_SHORT).show()
                    passwordList.clear()
                    updatePasswordViews()
                }
            }


            private fun onLoginSuccess(data: DataQrisNih) {
                val nominal = amount.toString()
                val referenceNumber = data.referenceNumber ?: ""
                val waktuTransaksi = data.createdDate ?: ""
                val namaPenerima = intent.getStringExtra("namaPenerima")

                val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
                val namaPengirim = sharedPref.getString("full_name", "")
                val rekeningNumber = sharedPref.getString("rekening_number", "")

                // Prepare the intent with extra data
                val intent = Intent(this@QrisVerificationActivity, QrisReceiptActivity::class.java).apply {
                    putExtra("nominal", nominal)
                    putExtra("referenceNumber", referenceNumber)
                    putExtra("waktuTransaksi", waktuTransaksi)
                    putExtra("namaPenerima", namaPenerima)
                    putExtra("namaPengirim", namaPengirim)
                    putExtra("rekeningNumber", rekeningNumber)
                }

                // Start the activity
                startActivity(intent)
                ProgressDialogUtils.hideProgressDialog()
            }


            private fun onError(errorMessage: String) {
                ProgressDialogUtils.hideProgressDialog()
                showSnackBar(errorMessage)
            }

            private fun showSnackBar(message: String) {
                SnackbarUtils.showWithDismissAction(binding.root, message)
            }

            override fun onFailure(call: Call<PinValidationResponse>, t: Throwable) {
                // Handle error
                Toast.makeText(this@QrisVerificationActivity, "Failed to validate PIN: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}