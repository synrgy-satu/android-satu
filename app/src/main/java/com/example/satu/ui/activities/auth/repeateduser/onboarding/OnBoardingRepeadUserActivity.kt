package com.example.satu.ui.activities.auth.repeateduser.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.databinding.ActivityOnBoardingRepeadUserBinding
import com.example.satu.ui.activities.auth.repeateduser.RepeadLoginActivity

class OnBoardingRepeadUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingRepeadUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingRepeadUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLoginOnboarding.setOnClickListener {
            val mainIntent = Intent(this@OnBoardingRepeadUserActivity, RepeadLoginActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}