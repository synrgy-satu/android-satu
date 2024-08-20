package com.example.satu.ui.activities.auth.newuser.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.ui.MainActivity
import com.example.satu.R

class LoginSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_success)
        Handler().postDelayed({
            val mainIntent = Intent(this@LoginSuccessActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000L)
    }
}