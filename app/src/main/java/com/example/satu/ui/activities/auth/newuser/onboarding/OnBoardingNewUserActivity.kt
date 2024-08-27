package com.example.satu.ui.activities.auth.newuser.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityOnBoardingNewUserBinding
import com.example.satu.ui.activities.auth.newuser.login.LoginEmailActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterEmailActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterRekeningActivity

class OnBoardingNewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingNewUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingNewUserBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply {
            btnLoginOnboarding.setOnClickListener {
                navigateTo(LoginEmailActivity::class.java)
            }
            btnRegisOnboarding.setOnClickListener {
                navigateTo(RegisterRekeningActivity::class.java)
            }
        }
    }

    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(this, destination))
    }
}
