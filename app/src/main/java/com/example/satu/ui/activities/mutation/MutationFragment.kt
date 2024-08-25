package com.example.satu.ui.activities.mutation

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.satu.R
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.button.MaterialButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MutationFragment : Fragment(), DateRangeCallback, TransactionCallback {

    private lateinit var tvDateRange: MaterialTextView
    private lateinit var tvAccountSource: MaterialTextView
    private lateinit var tvAccountSourceNumber: MaterialTextView
    private lateinit var tvTransactionType: MaterialTextView
    private lateinit var tvMutationAccountName: MaterialTextView
    private lateinit var tvMenuMutationTransactionType: MaterialTextView
    private lateinit var tvMenuMutationPeriode: MaterialTextView
    private lateinit var btnCheckMutation: MaterialButton
    private lateinit var llAccountSource: LinearLayout
    private lateinit var llMutationPeriode: LinearLayout
    private lateinit var llTransactionType: LinearLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mutation, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvDateRange = view.findViewById(R.id.tvMutationAccountDateRange)
        tvAccountSource = view.findViewById(R.id.tvMutationAccountSourceName)
        tvAccountSourceNumber = view.findViewById(R.id.tvMutationAccountSourceNumber)
        tvTransactionType = view.findViewById(R.id.tvMutationAccountTransactionType)
        btnCheckMutation = view.findViewById(R.id.btnCheckMutation)
        tvMutationAccountName = view.findViewById(R.id.tvMutationAccountName)
        llAccountSource = view.findViewById(R.id.llAccountSource)
        llMutationPeriode = view.findViewById(R.id.llMutationPeriode)
        llTransactionType = view.findViewById(R.id.llTransactionType)
        sharedPreferences = requireContext().getSharedPreferences("MutationPrefs", Context.MODE_PRIVATE)

        restoreValues()

        llAccountSource.setOnClickListener {
            val accountSourceBottomSheet = AccountSourceBottomSheetFragment()
            accountSourceBottomSheet.show(childFragmentManager, accountSourceBottomSheet.tag)
        }

        llMutationPeriode.setOnClickListener {
            val dateRangeBottomSheet = DateRangeBottomSheetFragment()
            dateRangeBottomSheet.show(childFragmentManager, dateRangeBottomSheet.tag)
        }

        llTransactionType.setOnClickListener {
            val transactionBottomSheetFragment = TransactionBottomSheetFragment()
            transactionBottomSheetFragment.setTransactionCallback(this)
            transactionBottomSheetFragment.show(childFragmentManager, transactionBottomSheetFragment.tag)
        }

        btnCheckMutation.isEnabled = false
        updateButtonColor()

        tvDateRange.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> checkButtonState() }
        tvAccountSource.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> checkButtonState() }
        tvTransactionType.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> checkButtonState() }
        tvMutationAccountName.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> checkButtonState() }

        btnCheckMutation.setOnClickListener {
            if (btnCheckMutation.isEnabled) {
                val processedDateRange: String = processDateRange()
                with(sharedPreferences.edit()) {
                    putString("dateRangeSelected", processedDateRange)
                    putString("transactionTypeSelected", tvTransactionType.text.toString())
                    putString("accountNameSelected", tvMutationAccountName.text.toString())
                    putString("accountSourceSelected", tvAccountSource.text.toString())
                    putString("accountSourceNumberSelected", tvAccountSourceNumber.text.toString())
                    apply()
                }

                // Retrieve the values from SharedPreferences for checking
                val dateRangeSelected = sharedPreferences.getString("dateRangeSelected", "")
                val transactionTypeSelected = sharedPreferences.getString("transactionTypeSelected", "")
                val accountNameSelected = sharedPreferences.getString("accountNameSelected", "")
                val accountSourceSelected = sharedPreferences.getString("accountSourceSelected", "")
                val accountSourceNumberSelected = sharedPreferences.getString("accountSourceNumberSelected", "")

                // Create a single message with all the values
                val message = """
                    Date Range: $dateRangeSelected
                    Transaction Type: $transactionTypeSelected
                    Account Name: $accountNameSelected
                    Account Source: $accountSourceSelected
                    Account Source Number: $accountSourceNumberSelected
                """.trimIndent()

                // Display the toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                openMutationVerificationFragment()
            }
        }
    }

    override fun onDateRangeSelected(dateRange: String) {
        tvDateRange.text = dateRange
        checkButtonState()
    }
    fun updateDateRange(dateRange: String) {
        val tvDateRange: MaterialTextView? = view?.findViewById(R.id.tvMutationAccountDateRange)
        tvDateRange?.text = dateRange // Update the text view with the selected date range
        checkButtonState()
    }

    override fun onTransactionTypeSelected(transactionType: String) {
        tvTransactionType.text = transactionType
        checkButtonState()
    }

    fun updateAccountSource(account: AccountSource) {
        tvMutationAccountName.text = account.accountName
        view?.findViewById<MaterialTextView>(R.id.tvMutationAccountSourceNumber)?.apply {
            text = account.accountNumber
            visibility = View.VISIBLE
        }
        view?.findViewById<MaterialTextView>(R.id.tvMutationAccountSourceName)?.apply {
            text = account.accountSource
            visibility = View.VISIBLE
        }
        checkButtonState()
    }

    private fun checkButtonState() {
        val dateRangeSelected = tvDateRange.text.toString()
        val transactionTypeSelected = tvTransactionType.text.toString()
        val accountNameSelected = tvMutationAccountName.text.toString()

        val allFieldsFilled = dateRangeSelected.isNotEmpty() && transactionTypeSelected.isNotEmpty() && accountNameSelected.isNotEmpty()
        btnCheckMutation.isEnabled = allFieldsFilled
        updateButtonColor()
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

    private fun restoreValues() {
        val dateRangeSelected = sharedPreferences.getString("dateRangeSelected", "")
        val transactionTypeSelected = sharedPreferences.getString("transactionTypeSelected", "")
        val accountNameSelected = sharedPreferences.getString("accountNameSelected", "")
        val accountSourceSelected = sharedPreferences.getString("accountSourceSelected", "")
        val accountNumberSelected = sharedPreferences.getString("accountSourceNumberSelected", "")

        tvDateRange.text = dateRangeSelected
        tvTransactionType.text = transactionTypeSelected
        tvMutationAccountName.text = accountNameSelected
        tvAccountSourceNumber.text = accountNumberSelected
        tvAccountSource.text = accountSourceSelected

        checkButtonState()
    }

    private fun openMutationVerificationFragment() {
        val mutationVerificationFragment = MutationVerificationFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mutationVerificationFragment)
            .addToBackStack(null)
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processDateRange(): String {
        val dateRangeText = tvDateRange.text.toString()
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))

        return when (dateRangeText) {
            "Hari ini" -> "hari ini"
            "7 hari terakhir" -> "tujuh hari terakhir"
            "30 hari terakhir" -> currentDate.minusDays(30).format(formatter)
            else -> {
                if (dateRangeText.matches(Regex("""\d{1,2} \w+ \d{4} - \d{1,2} \w+ \d{4}"""))) {
                    // If the format matches "13 Agustus 2024 - 14 Agustus 2024"
                    dateRangeText
                } else {
                    // If the format is "Januari 2024" or "Agustus 2024"
                    dateRangeText
                }
            }
        }
    }

}
