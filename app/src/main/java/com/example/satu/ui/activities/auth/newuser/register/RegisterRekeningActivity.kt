package com.example.satu.ui.activities.auth.newuser.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityOnBoardingNewUserBinding
import com.example.satu.databinding.ActivityRegisterRekeningBinding
import com.example.satu.ui.activities.auth.newuser.login.LoginEmailActivity
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.loader.LoaderRegisterRekeningActivity
import com.example.satu.ui.factory.AuthViewModelFactory
import com.example.satu.ui.viewmodel.RegisterViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.Result
import com.example.satu.utils.SnackbarUtils
import java.util.Calendar

class RegisterRekeningActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels {
        AuthViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityRegisterRekeningBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }
    private fun setupClickListeners() = with(binding) {
        btnNext.setOnClickListener {
            handleRegister()
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@RegisterRekeningActivity, OnBoardingNewUserActivity::class.java))
        }
    }

  private fun handleRegister() {
        val (rekening, bulan, tahun) = binding.run {
            arrayOf(
                etInputNumberRek.text.toString().trim(),
                etInputBulan.text.toString().trim(),
                etInputTahun.text.toString().trim(),
            )
        }
      if (!binding.cbCondition.isChecked) {
          showSnackBar("Anda harus menyetujui persyaratan dan kebijakan")
          return
      }
      if (rekening.length != 16 || !rekening.all { it.isDigit() }) {
          showSnackBar("Nomor rekening harus terdiri dari 16 digit angka")
          return
      }
      val bulanInt = bulan.toIntOrNull()
      if (bulanInt == null || bulanInt < 1 || bulanInt > 12) {
          showSnackBar("Format input bulan salah. Bulan harus antara 1 dan 12")
          return
      }
      val tahunInt = tahun.toIntOrNull()
      val currentYear = Calendar.getInstance().get(Calendar.YEAR)
      if (tahunInt == null || tahunInt < currentYear) {
          showSnackBar("Tahun tidak boleh  melebihi tahun sekarang")
          return
      }

        viewModel.register(cardNumber = rekening).observe(this) { result ->
                when (result) {
                    is Result.Loading -> ProgressDialogUtils.showProgressDialog(this@RegisterRekeningActivity)
                    is Result.Success -> onRegisterSuccess()
                    is Result.Error -> onRegisterError(result.error)
                }
            }
        }
    private fun onRegisterSuccess() {
        ProgressDialogUtils.hideProgressDialog()
        val rekening = binding.etInputNumberRek.text.toString().trim()
        val intent = Intent(this@RegisterRekeningActivity, LoaderRegisterRekeningActivity::class.java)

        intent.putExtra("REKENING", rekening)
        startActivity(intent)
    }

    private fun onRegisterError(errorMessage: String) {
        ProgressDialogUtils.hideProgressDialog()
        showSnackBar(errorMessage)
    }
    private fun showSnackBar(messageResId: Any) {
        SnackbarUtils.showWithDismissAction(binding.root, messageResId)
    }

}
