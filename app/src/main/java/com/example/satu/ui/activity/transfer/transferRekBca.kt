package com.example.satu.ui.activity.transfer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.satu.R
import com.example.satu.ui.adapter.rv.transferFavAdapter

class transferRekBca : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: transferFavAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    // Sample data for demonstration. Replace this with your actual data.
    private val myDataset: Array<Rekening> = arrayOf(
        Rekening("Yazid", "1234678", R.drawable.image_profil_bca),
        Rekening("Yazid", "1234678", R.drawable.image_profil_bca),
        Rekening("Yazid", "1234678", R.drawable.image_profil_bca),
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_rek_bca)

        recyclerView = findViewById(R.id.rv_fav)

        // Set up LayoutManager with horizontal orientation
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        // Set up Adapter
        adapter = transferFavAdapter(myDataset)
        recyclerView.adapter = adapter
    }
}

// Data class for Rekening
data class Rekening(val name: String, val rekening: String, val imageResId: Int)
