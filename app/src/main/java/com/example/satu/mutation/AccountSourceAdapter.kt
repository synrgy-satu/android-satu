package com.example.satu.mutation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        private val tvAccountNumber: MaterialTextView = itemView.findViewById(R.id.tvAccountNumber)
        private val tvAccountName: MaterialTextView = itemView.findViewById(R.id.tvAccountName)
        private val tvAccountSource: MaterialTextView = itemView.findViewById(R.id.tvAccountSource)
        fun bind(account: AccountSource, onItemClick: (AccountSource) -> Unit) {
            tvAccountNumber.text = account.accountNumber
            tvAccountName.text = account.accountName
            tvAccountSource.text = account.accountSource
            itemView.setOnClickListener { onItemClick(account) }
        }
    }
}
