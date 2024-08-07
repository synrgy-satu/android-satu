package com.example.satu.mutation

import MutationVerificationFragment
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.satu.R
import com.example.satu.callbacks.DateRangeCallback
import com.example.satu.callbacks.TransactionCallback
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.button.MaterialButton

class MutationFragment : Fragment(), DateRangeCallback, TransactionCallback {

    private lateinit var tvDateRange: MaterialTextView
    private lateinit var tvAccountSource: MaterialTextView
    private lateinit var tvTransactionType: MaterialTextView
    private lateinit var tvMutationAccountName: MaterialTextView
    private lateinit var tvMenuMutationTransactionType: MaterialTextView
    private lateinit var tvMenuMutationPeriode: MaterialTextView
    private lateinit var btnCheckMutation: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mutation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvDateRange = view.findViewById(R.id.tvMutationAccountDateRange)
        tvAccountSource = view.findViewById(R.id.tvMenuMutationAccountSource)
        tvMenuMutationTransactionType = view.findViewById(R.id.tvMenuMutationTransactionType)
        tvMenuMutationPeriode = view.findViewById(R.id.tvMenuMutationPeriode)
        tvTransactionType = view.findViewById(R.id.tvMutationAccountTransactionType)
        btnCheckMutation = view.findViewById(R.id.btnCheckMutation)
        tvMutationAccountName = view.findViewById(R.id.tvMutationAccountName) // Initialize

        tvAccountSource.setOnClickListener {
            val accountSourceBottomSheet = AccountSourceBottomSheetFragment()
            accountSourceBottomSheet.show(childFragmentManager, accountSourceBottomSheet.tag)
        }

        tvMenuMutationPeriode.setOnClickListener {
            val dateRangeBottomSheet = DateRangeBottomSheetFragment()
            dateRangeBottomSheet.setDateRangeCallback(this) // Set the callback
            dateRangeBottomSheet.show(childFragmentManager, dateRangeBottomSheet.tag)
        }

        tvMenuMutationTransactionType.setOnClickListener {
            val transactionBottomSheetFragment = TransactionBottomSheetFragment()
            transactionBottomSheetFragment.setTransactionCallback(this) // Set the callback
            transactionBottomSheetFragment.show(childFragmentManager, transactionBottomSheetFragment.tag)
        }

        // Initialize the button state
        btnCheckMutation.isEnabled = false
        updateButtonColor() // Update button color initially

        // Add listeners to update button state
        tvDateRange.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> checkButtonState() }
        tvAccountSource.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> checkButtonState() }
        tvTransactionType.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> checkButtonState() }
        tvMutationAccountName.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> checkButtonState() }

        // Set OnClickListener for the button
        btnCheckMutation.setOnClickListener {
            if (btnCheckMutation.isEnabled) {
                openMutationVerificationFragment()
            }
        }
    }

    override fun onDateRangeSelected(dateRange: String) {
        view?.let {
            val tvDateRange: MaterialTextView = it.findViewById(R.id.tvMutationAccountDateRange)
            tvDateRange.text = dateRange
            checkButtonState() // Check button state after updating date range
        }
    }

    override fun onTransactionTypeSelected(transactionType: String) {
        view?.let {
            val tvTransactionType: MaterialTextView = it.findViewById(R.id.tvMutationAccountTransactionType)
            tvTransactionType.text = transactionType
            checkButtonState() // Check button state after updating transaction type
        }
    }

    fun updateAccountSource(account: AccountSource) {
        view?.let {
            val tvMutationAccountName: MaterialTextView = it.findViewById(R.id.tvMutationAccountName)
            val tvMutationAccountSourceNumber: MaterialTextView = it.findViewById(R.id.tvMutationAccountSourceNumber)
            val tvMutationAccountSourceName: MaterialTextView = it.findViewById(R.id.tvMutationAccountSourceName)

            // Update the TextViews with the account data
            tvMutationAccountName.text = account.accountName
            tvMutationAccountSourceNumber.text = account.accountNumber
            tvMutationAccountSourceName.text = account.accountSource
            tvMutationAccountSourceNumber.visibility = View.VISIBLE
            tvMutationAccountSourceName.visibility = View.VISIBLE

            checkButtonState() // Check button state after updating account source
        }
    }

    private fun checkButtonState() {
        val dateRangeSelected = tvDateRange.text.toString()
        val transactionTypeSelected = tvTransactionType.text.toString()
        val accountNameSelected = tvMutationAccountName.text.toString()

        // Enable the button only if all required fields are filled
        val allFieldsFilled = dateRangeSelected.isNotEmpty() && transactionTypeSelected.isNotEmpty() && accountNameSelected.isNotEmpty()
        btnCheckMutation.isEnabled = allFieldsFilled
        updateButtonColor() // Update button color based on state
    }

    private fun updateButtonColor() {
        val primaryColor = ContextCompat.getColor(requireContext(), R.color.primary)
        val disabledColor = ContextCompat.getColor(requireContext(), R.color.disable)
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)

        btnCheckMutation.backgroundTintList = if (btnCheckMutation.isEnabled) {
            btnCheckMutation.setTextColor(whiteColor)
            ColorStateList.valueOf(primaryColor)
        } else {
            ColorStateList.valueOf(disabledColor)
        }
    }

    private fun openMutationVerificationFragment() {
        val mutationVerificationFragment = MutationVerificationFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mutationVerificationFragment) // Use your container ID
            .addToBackStack(null) // Optional: add to back stack if you want to allow navigation back
            .commit()
    }
}
