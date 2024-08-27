package com.example.satu.ui.activities.transfer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.data.model.response.transfer.DataCardRekening
import com.example.satu.databinding.ActivityTransferAddNewRekeningBinding
import com.example.satu.ui.factory.TransferViewModelfactory
import com.example.satu.ui.viewmodel.TransferViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.SnackbarUtils

class TransferAddNewRekeningActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferAddNewRekeningBinding
    private val viewModel: TransferViewModel by viewModels {
        TransferViewModelfactory.getInstance(application)
    }
    private lateinit var rekening: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferAddNewRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }
    // kasih kondisi kalo misal dia ngii nomor rekneingnya sendiri maka ga bisa lanjut
    private fun setupClickListeners() = with(binding){
        btnNext.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
           if (validateInputs()) {
                rekening = binding.etAddNumber.text.toString().trim()

               rekening.toLongOrNull()?.let { it1 ->
                   viewModel.getCardRekening(it1).observe(this@TransferAddNewRekeningActivity) { result ->
                       when (result) {
                           is com.example.common.Result.Loading -> ProgressDialogUtils.showProgressDialog(this@TransferAddNewRekeningActivity)
                           is com.example.common.Result.Success -> result.data.data?.let { onLoginSuccess(it) }
                           is com.example.common.Result.Error -> onLoginError(result.error)
                       }
                   }
               }
            }
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferAddNewRekeningActivity, TransferBcaActivity::class.java))
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val rekeningNumberUser = sharedPreferences.getString("rekening_number", "")

        rekening = binding.etAddNumber.text.toString().trim()
        if(rekening == rekeningNumberUser){
            showSnackBar("Rekening salah, tidak boleh menginput rekening sendiri")
            return false
        }else{
            return true
        }
    }

    private fun onLoginSuccess(data: DataCardRekening) {
        val namarekeningtujuan = data.name
        val sharedPref = getSharedPreferences("UserTransferRekening", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("targetRekeningNumber", rekening)
            putString("namaRekeningTujuan", namarekeningtujuan)
            apply()
        }
        ProgressDialogUtils.hideProgressDialog()
        startActivity(Intent(this@TransferAddNewRekeningActivity, TransferNowActivity::class.java))
        finish()
    }
    private fun onLoginError(errorMessage: String) {
        ProgressDialogUtils.hideProgressDialog()
        showSnackBar(errorMessage)
    }

    private fun showSnackBar(message: String) {
        SnackbarUtils.showWithDismissAction(binding.root, message)
    }
}