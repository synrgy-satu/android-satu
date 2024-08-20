package com.example.satu.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.satu.R
import com.example.satu.databinding.FragmentHomeBinding
import com.example.satu.databinding.FragmentProfileBinding
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.mutation.MutationActivity

class ProfileFragment : Fragment() {
        private var _binding: FragmentProfileBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentProfileBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.btnKeluar.setOnClickListener {
                val intent = Intent(activity, OnBoardingNewUserActivity::class.java)
                startActivity(intent)
            }
    }
}