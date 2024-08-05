package com.example.satu.ui.activities.loader

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterEmailActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterPasswordActivity

class LoaderRegisterRekeningActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_loader_register_rekening)
        Handler().postDelayed({
            val mainIntent = Intent(this@LoaderRegisterRekeningActivity, RegisterEmailActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}