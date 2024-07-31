package com.example.satu.mutation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.satu.R
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.button.MaterialButton

class MutationFragment : Fragment() {

    //private val viewModel: MutationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mutation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        val tvAccountSource: MaterialTextView = view.findViewById(R.id.tvMutationAccountSource)
        val tvDateRange: MaterialTextView = view.findViewById(R.id.tvMutationAccountDateRange)
        val tvTransactionType: MaterialTextView = view.findViewById(R.id.tvMutationAccountTransactionType)
       // val applyButton: MaterialButton = view.findViewById(R.id.btnApply)

        // Set up listeners
        tvAccountSource.setOnClickListener {
            val accountSourceBottomSheet = AccountSourceBottomSheetFragment()
            accountSourceBottomSheet.show(childFragmentManager, accountSourceBottomSheet.tag)
        }

        tvDateRange.setOnClickListener {
            val dateRangeBottomSheet = DateRangeBottomSheetFragment()
            dateRangeBottomSheet.show(childFragmentManager, dateRangeBottomSheet.tag)
        }

    }

    fun updateAccountSource(account: AccountSource) {
        val tvAccountSource: MaterialTextView? = view?.findViewById(R.id.tvMutationAccountSource)
        tvAccountSource?.text = account.accountNumber // Update the text view with the selected account number
    }

    fun updateDateRange(dateRange: String) {
        val tvDateRange: MaterialTextView? = view?.findViewById(R.id.tvMutationAccountDateRange)
        tvDateRange?.text = dateRange // Update the text view with the selected date range
    }
}
