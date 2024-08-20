package com.example.satu.ui.activity.transfer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.databinding.ActivitySelectTimeBinding
import java.text.SimpleDateFormat
import java.util.*

class SelectTimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectTimeBinding
    private var selectedDate: Date? = null
    private var selectedTime: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        updateDateAndTimeDisplay()
    }

    private fun setupClickListeners() = with(binding) {
        btnChooseTanggal.setOnClickListener {
            showDatePicker()
        }
        btnChooseWaktu.setOnClickListener {
            showTimePicker()
        }
        btnSave.setOnClickListener {
            if (isDateAndTimeInFuture()) {
                val intent = Intent(this@SelectTimeActivity, transferNow::class.java).apply {
                    putExtra("selectedDate", SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(selectedDate))
                    putExtra("selectedTime", SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedTime))
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@SelectTimeActivity, "Tanggal dan waktu harus di masa depan.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = android.app.DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                updateDateAndTimeDisplay()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val timePicker = android.app.TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                selectedTime = calendar.time
                updateDateAndTimeDisplay()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }

    private fun updateDateAndTimeDisplay() {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        binding.btnChooseTanggal.text = selectedDate?.let { dateFormat.format(it) } ?: "Pilih Tanggal"
        binding.btnChooseWaktu.text = selectedTime?.let { timeFormat.format(it) } ?: "Pilih Waktu"

        updateButtonStates()
    }

    private fun updateButtonStates() {
        val isInPast = !isDateAndTimeInFuture()
        binding.btnSave.isEnabled = !isInPast
    }

    private fun isDateAndTimeInFuture(): Boolean {
        if (selectedDate == null || selectedTime == null) return false

        val calendar = Calendar.getInstance().apply {
            time = selectedDate
            set(Calendar.HOUR_OF_DAY, (selectedTime?.hours ?: 0))
            set(Calendar.MINUTE, (selectedTime?.minutes ?: 0))
        }

        return calendar.time.after(Date())
    }
}
