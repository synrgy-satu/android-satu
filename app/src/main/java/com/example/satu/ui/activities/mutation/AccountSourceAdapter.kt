package com.example.satu.ui.activities.mutation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.satu.R
import com.google.android.material.textview.MaterialTextView

class AccountSourceAdapter(
    private val accounts: List<AccountSource>,
    private val onItemClick: (AccountSource) -> Unit
) : RecyclerView.Adapter<AccountSourceAdapter.AccountSourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountSourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account_source, parent, false)
        return AccountSourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountSourceViewHolder, position: Int) {
        val account = accounts[position]
        holder.bind(account, onItemClick)
    }

    override fun getItemCount(): Int = accounts.size

    class AccountSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivAccountImage: ImageView = itemView.findViewById(R.id.ivAccountImage)
        private val tvAccountNumber: MaterialTextView = itemView.findViewById(R.id.tvAccountNumber)
        private val tvAccountName: MaterialTextView = itemView.findViewById(R.id.tvAccountName)
        private val tvAccountSource: MaterialTextView = itemView.findViewById(R.id.tvAccountSource)

        fun bind(account: AccountSource, onItemClick: (AccountSource) -> Unit) {
            tvAccountNumber.text = account.accountNumber
            tvAccountName.text = account.accountName
            tvAccountSource.text = account.accountSource
            val type = tvAccountSource.text.toString().lowercase();

            // Set the image resource based on some property of account, e.g., account type
            // You can use a placeholder or dynamic image based on your requirements
            val imageResId = when (type) {
                "SAVER".lowercase() -> R.drawable.saver
                "PRIORITAS".lowercase() -> R.drawable.prioritas
                else -> R.drawable.edu
            }
            ivAccountImage.setImageResource(imageResId)

            itemView.setOnClickListener { onItemClick(account) }
        }
    }
}
