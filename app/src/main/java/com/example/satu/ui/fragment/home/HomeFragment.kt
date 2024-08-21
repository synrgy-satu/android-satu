package com.example.satu.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.satu.R
import com.example.satu.databinding.FragmentHomeBinding
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.maintance.MaintanceActivity
import com.example.satu.ui.activities.mutation.DateRangeViewModel
import com.example.satu.ui.activities.mutation.MutationActivity
import com.example.satu.ui.factory.AuthViewModelFactory
import com.example.satu.ui.viewmodel.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            val intent = Intent(activity, MaintanceActivity::class.java)
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

    private fun showLogoutDialog() {
        val customDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_logout, null)
        val alertDialog = buildAlertDialog(customDialogView)
        val yesButton = customDialogView.findViewById<Button>(R.id.btnyes)
        val noButton = customDialogView.findViewById<Button>(R.id.btnno)
        yesButton.setOnClickListener {
            handleYesButtonClick()
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
        viewModel.deleteUserLogin()
        startActivity(Intent(requireContext(), OnBoardingNewUserActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}