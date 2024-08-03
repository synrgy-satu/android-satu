package com.example.satu.ui.adapter.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.satu.R
import com.example.satu.ui.activity.transfer.Rekening
import de.hdodenhof.circleimageview.CircleImageView

class transferFavAdapter(private val myDataset: Array<Rekening>) :
    RecyclerView.Adapter<transferFavAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imageView: CircleImageView = v.findViewById(R.id.iv_picture)
        val nameTextView: TextView = v.findViewById(R.id.tv_name)
        val rekeningTextView: TextView = v.findViewById(R.id.tv_rekening)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fav_rekening, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = myDataset[position]
        holder.imageView.setImageResource(currentItem.imageResId)
        holder.nameTextView.text = currentItem.name
        holder.rekeningTextView.text = currentItem.rekening
    }

    override fun getItemCount() = myDataset.size
}