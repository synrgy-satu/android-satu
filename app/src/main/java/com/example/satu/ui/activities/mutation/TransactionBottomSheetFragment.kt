package com.example.satu.ui.activities.mutation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.satu.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class TransactionBottomSheetFragment : BottomSheetDialogFragment() {

    private var selectedTransactionType: String? = null
    private var callback: TransactionCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_transaction_type_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rbAll: RadioButton = view.findViewById(R.id.rbAll)
        val rbTransactionIn: RadioButton = view.findViewById(R.id.rbTransactionIn)
        val rbTransactionOut: RadioButton = view.findViewById(R.id.rbTransactionOut)
        val radioGroupTransaction: RadioGroup = view.findViewById(R.id.radioGroupDateRange)
        val tvAll: TextView = view.findViewById(R.id.tvAll)
        val tvTransactionIn: TextView = view.findViewById(R.id.tvTransactionIn)
        val tvTransactionOut: TextView = view.findViewById(R.id.tvTransactionOut)
        val applyButton: MaterialButton = view.findViewById(R.id.applyButton)

        fun onRadioButtonClicked(checkedRadioButton: RadioButton) {
            rbAll.isChecked = false
            rbTransactionIn.isChecked = false
            rbTransactionOut.isChecked = false

            checkedRadioButton.isChecked = true

            applyButton.isEnabled = true
            applyButton.setBackgroundColor(resources.getColor(R.color.primary))
            applyButton.setTextColor(resources.getColor(R.color.white))

            selectedTransactionType = when (checkedRadioButton.id) {
                R.id.rbAll -> tvAll.text.toString()
                R.id.rbTransactionIn -> tvTransactionIn.text.toString()
                R.id.rbTransactionOut -> tvTransactionOut.text.toString()
                else -> null
            }
        }

        rbAll.setOnClickListener { onRadioButtonClicked(rbAll) }
        rbTransactionIn.setOnClickListener { onRadioButtonClicked(rbTransactionIn) }
        rbTransactionOut.setOnClickListener { onRadioButtonClicked(rbTransactionOut) }

        applyButton.setOnClickListener {
            if (selectedTransactionType != null) {
                callback?.onTransactionTypeSelected(selectedTransactionType!!)
                dismiss()
            } else {
                Toast.makeText(context, "Pilih jenis transaksi terlebih dahulu", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setTransactionCallback(callback: TransactionCallback) {
        this.callback = callback
    }
}
