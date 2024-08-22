package com.example.satu.ui.activity.qris

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.satu.databinding.ActivityCameraPermissionBinding

class cameraPermission : AppCompatActivity() {

    private lateinit var binding: ActivityCameraPermissionBinding

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val GALLERY_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        btnFlash.setOnClickListener {
            if (checkAndRequestPermissions()) {
                toggleFlash()
            }
        }

        btnGallery.setOnClickListener {
            openGallery()
        }

        btnKode.setOnClickListener {
            val intent = Intent(this@cameraPermission, scanQrAddNominal::class.java)
            startActivity(intent)
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            false
        } else {
            true
        }
    }

    private fun toggleFlash() {
        Toast.makeText(this, "Flash", Toast.LENGTH_SHORT).show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Mengizinkan Kamera", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Izin Kamera ditolak", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            Toast.makeText(this, "Image selected from gallery", Toast.LENGTH_SHORT).show()
        }
    }
}
