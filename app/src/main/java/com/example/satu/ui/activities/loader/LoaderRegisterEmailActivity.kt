package com.example.satu.ui.activities.loader

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.ui.activities.auth.newuser.register.RegisterEmailActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterPasswordActivity

class LoaderRegisterEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loader_register_email)
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val rekening = intent.getLongExtra("REKENING", -1L)

        Handler().postDelayed({
            val mainIntent = Intent(this@LoaderRegisterEmailActivity, RegisterPasswordActivity::class.java).apply {
                putExtra("EMAIL", email)
                putExtra("PHONE", phone)
                putExtra("REKENING", rekening)
            }
            startActivity(mainIntent)
            finish()
        }, 3000L)

    }
}