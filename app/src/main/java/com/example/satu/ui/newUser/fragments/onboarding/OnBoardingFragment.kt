package com.example.satu.ui.newUser.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.satu.R
import com.example.satu.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        binding.btnLoginOnboarding.setOnClickListener{
            it.findNavController().navigate(R.id.action_onBoardingFragment_to_loginActivity)
        }

        binding.btnRegisOnboarding.setOnClickListener{
            it.findNavController().navigate(R.id.action_onBoardingFragment_to_registerRekeningAcitivty)
        }

        return binding.root
    }

}