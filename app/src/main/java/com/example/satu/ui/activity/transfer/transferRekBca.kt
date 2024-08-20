package com.example.satu.ui.activity.transfer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.satu.R
import com.example.satu.databinding.ActivityTransferRekBcaBinding
import com.example.satu.ui.adapter.rv.transferFavAdapter

class transferRekBca : AppCompatActivity() {
    private lateinit var binding: ActivityTransferRekBcaBinding

    // Sample data for demonstration. Replace this with your actual data.
    private val myDataset: Array<Rekening> = arrayOf(
        Rekening("Yazid", "1234678", R.drawable.image_profil_bca),
        Rekening("Yazid", "1234678", R.drawable.image_profil_bca),
        Rekening("Yazid", "1234678", R.drawable.image_profil_bca),
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferRekBcaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView with LayoutManager and Adapter
        binding.rvFav.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFav.adapter = transferFavAdapter(myDataset)

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        topAppBar.setOnClickListener {
            startActivity(Intent(this@transferRekBca, selectDestinationTransfer::class.java))
        }
        btnAdd.setOnClickListener {
            startActivity(Intent(this@transferRekBca, addNewNumberRekening::class.java))
        }
    }
}

// Data class for Rekening
data class Rekening(val name: String, val rekening: String, val imageResId: Int)
