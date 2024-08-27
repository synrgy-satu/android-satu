package com.example.satu.ui.activities.qris

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.satu.databinding.ActivityQrisBinding
import com.example.satu.ui.activities.MainActivity
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrisBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var flashEnabled: Boolean = false
    private lateinit var cameraControl: CameraControl

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val GALLERY_REQUEST_CODE = 101
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startCamera() // Start camera if permission is granted
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(this, REQUIRED_PERMISSION) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        } else {
            startCamera()
        }

        setupClickListeners()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            val barcodeAnalyzer = BarcodeAnalyzer()

            val imageAnalysis = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(ContextCompat.getMainExecutor(this), barcodeAnalyzer)
                }

            try {
                cameraProvider.unbindAll()
                val camera: Camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
                cameraControl = camera.cameraControl
                Log.d("CameraX", "Camera started successfully")
            } catch (exc: Exception) {
                exc.printStackTrace()
                Log.e("CameraX", "Failed to start camera: ${exc.message}")
                Toast.makeText(this, "Failed to start camera", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun setupClickListeners() = with(binding) {
        btnFlash.setOnClickListener {
            toggleFlash()
        }

        btnGallery.setOnClickListener {
            openGallery()
        }

        topAppBar.setOnClickListener {
            val intent = Intent(this@QrisActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btnKode.setOnClickListener {
            val intent = Intent(this@QrisActivity, PayWithCodeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImage: Uri? = result.data?.data
                Toast.makeText(this, "Image selected from gallery", Toast.LENGTH_SHORT).show()
            }
        }

    private fun toggleFlash() {
        if (::cameraControl.isInitialized) {
            flashEnabled = !flashEnabled
            cameraControl.enableTorch(flashEnabled)
            val flashState = if (flashEnabled) "on" else "off"
            Toast.makeText(this, "Flash $flashState", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Camera not ready", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            Toast.makeText(this, "Image selected from gallery", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class BarcodeAnalyzer : ImageAnalysis.Analyzer {
        private val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_CODE_128, Barcode.FORMAT_CODE_39, Barcode.FORMAT_QR_CODE)
                .build()
        )

        // Flag untuk memastikan pemindaian hanya dilakukan sekali
        private var barcodeScanned: Boolean = false

        @OptIn(ExperimentalGetImage::class)
        override fun analyze(imageProxy: ImageProxy) {
            if (barcodeScanned) {
                imageProxy.close()
                return
            }

            val mediaImage = imageProxy.image ?: return
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue
                        Log.d("Barcode", "Barcode value: $rawValue")
                        // Mengatur flag untuk menandai bahwa barcode sudah dipindai
                        barcodeScanned = true

                        // Pindah halaman dengan nilai barcode
                        val intent = Intent(this@QrisActivity, NominalQrisActivity::class.java).apply {
                            putExtra("barcodeValue", rawValue)
                        }
                        startActivity(intent)
                        break
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Barcode", "Barcode scan failed", e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    override fun onResume() {
        super.onResume()
        if (allPermissionsGranted()) {
            startCamera()
        }
    }
}