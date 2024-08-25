package com.example.satu.ui.activities.mutation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.satu.R
import com.example.satu.databinding.ItemMutationBinding
import java.text.NumberFormat
import java.util.Locale

class MutationResultAdapter : ListAdapter<MutationData, MutationResultAdapter.MutationViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutationViewHolder {
        val binding = ItemMutationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MutationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MutationViewHolder, position: Int) {
        val mutationData = getItem(position)
        holder.bind(mutationData)
    }

    class MutationViewHolder(private val binding: ItemMutationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mutationData: MutationData) {
            binding.tvDate.text = mutationData.createdDate
            binding.tvHistory.text = mutationData.note

            // Format the amount with thousand separators
            val numberFormat = NumberFormat.getNumberInstance(Locale.US)
            val formattedAmount = numberFormat.format(mutationData.amount)

            // Set the amount color and format based on transaction type
            if (mutationData.jenisTransaksi == "TRANSAKSI_MASUK") {
                binding.tvAmount.text = "+Rp $formattedAmount.00"
                binding.tvAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
            } else {
                binding.tvAmount.text = "-Rp $formattedAmount.00"
                binding.tvAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MutationData>() {
        override fun areItemsTheSame(oldItem: MutationData, newItem: MutationData): Boolean {
            return oldItem.createdDate == newItem.createdDate && oldItem.note == newItem.note && oldItem.amount.toString() == newItem.amount.toString()
        }

        override fun areContentsTheSame(oldItem: MutationData, newItem: MutationData): Boolean {
            return oldItem == newItem
        }
    }
}
