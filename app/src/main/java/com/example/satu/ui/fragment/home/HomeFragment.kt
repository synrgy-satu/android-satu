package com.example.satu.ui.fragment.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.satu.R
import com.example.satu.data.model.response.auth.DataUser
import com.example.satu.data.model.response.user.DataCurrentUser
import com.example.satu.data.model.response.user.UserResponse
import com.example.satu.databinding.FragmentHomeBinding
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.maintance.MaintanceActivity
import com.example.satu.ui.activities.mutation.DateRangeViewModel
import com.example.satu.ui.activities.mutation.MutationActivity
import com.example.satu.ui.activities.transfer.TransferTujuanActivity
import com.example.satu.ui.factory.AuthViewModelFactory
import com.example.satu.ui.viewmodel.LoginViewModel
import com.example.satu.ui.viewmodel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.satu.utils.Result
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenUser: DataUser
    private var isSaldoVisible: Boolean = false
    private val viewModel: LoginViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireActivity().application)
    }

    private val userViewModel: UserViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun setupViews() {
        userViewModel.getToken().observe(viewLifecycleOwner) {
            tokenUser = it
            tokenUser.accessToken?.let { token -> userViewModel.getUser(token).observe(viewLifecycleOwner, ::handleDataUser) }
        }
    }
    private fun handleDataUser(result: Result<UserResponse>) {
        when (result) {
            is Result.Loading -> return
            is Result.Success -> result.data.data?.let { showUserProfile(it) }
            is Result.Error -> showToast(result.error)
        }
    }


    private fun showUserProfile(userProfile: DataCurrentUser) {
        with(binding) {
            val nominalAsli = userProfile.rekenings?.get(0)?.balance.toString()
            val bintang = "*********"

            tvNamaLengkap.text = userProfile.fullName.toString()
            tvNominal.text = bintang
            tvRekening.text = userProfile.rekenings?.get(0)?.rekeningNumber.toString()

            btnSalinRekening.setOnClickListener {
                salinRekeningKeClipboard(tvRekening.text.toString())
            }

            btnLihatSaldo.setOnClickListener {
                isSaldoVisible = !isSaldoVisible
                tvNominal.text = if (isSaldoVisible) nominalAsli else bintang
                val iconResId = if (isSaldoVisible) R.drawable.ic_eye_lihat else R.drawable.ic_eye_lihat
                btnLihatSaldo.setBackgroundResource(iconResId)
            }

            val fullName =  userProfile.fullName.toString()
            val rekeningNumber = userProfile.rekenings?.get(0)?.rekeningNumber.toString()
            val cardNumber = userProfile.rekenings?.get(0)?.cardNumber.toString()
            val balance = userProfile.rekenings?.get(0)?.balance.toString()
            val pin = userProfile.pin.toString()
            saveToSharedPreferences(fullName, rekeningNumber, cardNumber, balance, pin)

        }
    }

    private fun saveToSharedPreferences(fullName: String, rekeningNumber: String, cardNumber: String, balance: String, pin: String) {
        val sharedPref = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("full_name", fullName)
            putString("rekening_number", rekeningNumber)
            putString("card_number", cardNumber)
            putString("balance", balance)
            putString("pin", pin)
            apply()
        }
    }

    private fun salinRekeningKeClipboard(rekeningNumber: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Nomor Rekening", rekeningNumber)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Nomor rekening tersalin", Toast.LENGTH_SHORT).show()
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        binding.fiturMutasi.setOnClickListener {
            val intent = Intent(activity, MutationActivity::class.java)
            startActivity(intent)
        }
        binding.fiturInvestasi.setOnClickListener {
            val intent = Intent(activity, MaintanceActivity::class.java)
            startActivity(intent)
        }
        binding.fiturKredit.setOnClickListener {
            val intent = Intent(activity, MaintanceActivity::class.java)
            startActivity(intent)
        }
        binding.fiturTap.setOnClickListener {
            val intent = Intent(activity, MaintanceActivity::class.java)
            startActivity(intent)
        }
        binding.fiturPembayaran.setOnClickListener {
            val intent = Intent(activity, MaintanceActivity::class.java)
            startActivity(intent)
        }
        binding.fiturPembelian.setOnClickListener {
            val intent = Intent(activity, MaintanceActivity::class.java)
            startActivity(intent)
        }
        binding.fiturTransfer.setOnClickListener {
            val intent = Intent(activity, TransferTujuanActivity::class.java)
            startActivity(intent)
        }
        binding.btnRekening.setOnClickListener {
            val intent = Intent(activity, MaintanceActivity::class.java)
            startActivity(intent)
        }
        binding.btnKeluar.setOnClickListener {
            showLogoutDialog()
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showLogoutDialog() {
        val customDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_logout, null)
        val alertDialog = buildAlertDialog(customDialogView)
        val yesButton = customDialogView.findViewById<Button>(R.id.btnyes)
        val noButton = customDialogView.findViewById<Button>(R.id.btnno)
        yesButton.setOnClickListener {
            handleYesButtonClick()
            alertDialog.dismiss()
        }
        noButton.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
    private fun buildAlertDialog(customDialogView: View): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setView(customDialogView)
            .create()
    }
    private fun handleYesButtonClick() {
        lifecycleScope.launch {
            viewModel.deleteUserLogin()
            clearPasswordFromSharedPreferences()
            startActivity(Intent(requireContext(), OnBoardingNewUserActivity::class.java))
            requireActivity().finish()

        }
    }
    private fun clearPasswordFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove("user_password")
            apply()
        }
        val sharedPref = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        with(sharedPref.edit()) {
            clear()
            apply()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}