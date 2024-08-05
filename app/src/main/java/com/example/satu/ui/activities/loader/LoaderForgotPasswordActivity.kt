package com.example.satu.ui.activities.loader

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.ui.activities.auth.newuser.login.LoginEmailActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterPasswordActivity

class LoaderForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loader_forgot_password)
        Handler().postDelayed({
            val mainIntent = Intent(this@LoaderForgotPasswordActivity, LoginEmailActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000L)
    }
}