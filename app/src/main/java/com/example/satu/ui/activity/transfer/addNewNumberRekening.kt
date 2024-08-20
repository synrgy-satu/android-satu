package com.example.satu.ui.activity.transfer

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.R
import com.example.satu.databinding.ActivityAddNewNumberRekeningBinding

class addNewNumberRekening : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewNumberRekeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewNumberRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        topAppBar.setOnClickListener {
            startActivity(Intent(this@addNewNumberRekening, transferRekBca::class.java))
        }
        btnNext.setOnClickListener {
            val accountNumber = etAddNumber.text.toString()
            if (accountNumber.isEmpty()) {
                val toast = Toast.makeText(this@addNewNumberRekening, "Harap isi nomor rekening", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else {
                startActivity(Intent(this@addNewNumberRekening, transferNow::class.java))
            }
        }
    }
}
