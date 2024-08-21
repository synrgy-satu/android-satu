package com.example.satu.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.auth.repeateduser.RepeadLoginActivity
import com.example.satu.ui.factory.AuthViewModelFactory
import com.example.satu.ui.viewmodel.LoginViewModel

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 3000L
    private val viewModel: LoginViewModel by viewModels {
        AuthViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
//        Handler().postDelayed({
//            val mainIntent = Intent(this@SplashScreenActivity, OnBoardingNewUserActivity::class.java)
//            startActivity(mainIntent)
//            finish()
//        }, SPLASH_DISPLAY_LENGTH)

        viewModel.getUserLogin().observe(this) { user ->

            if (user.accessToken.isNullOrEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent =
                        Intent(this@SplashScreenActivity, OnBoardingNewUserActivity::class.java)
                    startActivity(intent)
                    finish()
                }, SPLASH_DISPLAY_LENGTH)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashScreenActivity, RepeadLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, SPLASH_DISPLAY_LENGTH)
            }

        }
    }
}