package com.example.satu.ui.fragment.profile

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
import com.example.satu.databinding.FragmentProfileBinding
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.factory.AuthViewModelFactory
import com.example.satu.ui.viewmodel.LoginViewModel
import com.example.satu.ui.viewmodel.UserViewModel
import com.example.satu.utils.Result
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
        private var _binding: FragmentProfileBinding? = null
        private val binding get() = _binding!!
        private lateinit var tokenUser: DataUser
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
            _binding = FragmentProfileBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setupViews()
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

            val sharedPrefToken = requireContext().getSharedPreferences("UserToken", Context.MODE_PRIVATE)

            with(sharedPrefToken.edit()) {
                clear()
                apply()
            }

        }
        private fun setupViews() {
            userViewModel.getToken().observe(viewLifecycleOwner) {
                tokenUser = it
                tokenUser.accessToken?.let { token ->
                    userViewModel.getUser(token).observe(viewLifecycleOwner, ::handleDataUser)
                }
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
                tvAccountName.text = userProfile.fullName.toString()
                tvAccountEmail.text = userProfile.emailAddress.toString()
            }
        }

        private fun showToast(message: String) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
}