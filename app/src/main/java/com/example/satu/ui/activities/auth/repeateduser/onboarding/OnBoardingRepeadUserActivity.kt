package com.example.satu.ui.activities.auth.repeateduser.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityLoginSuccessBinding
import com.example.satu.databinding.ActivityOnBoardingRepeadUserBinding
import com.example.satu.ui.MainActivity
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