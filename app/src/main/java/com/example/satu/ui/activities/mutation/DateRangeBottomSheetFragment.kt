package com.example.satu.ui.activities.mutation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.satu.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import android.widget.LinearLayout
import java.util.*

class DateRangeBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: DateRangeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_date_range_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rbTodayDateRange: RadioButton = view.findViewById(R.id.rbTodayDateRange)
        val rb7DaysDateRange: RadioButton = view.findViewById(R.id.rb7DaysDateRange)
        val rb30DaysDateRange: RadioButton = view.findViewById(R.id.rb30DaysDateRange)
        val rbChooseDateRange: RadioButton = view.findViewById(R.id.rbChooseDateRange)
        val applyButton: MaterialButton = view.findViewById(R.id.applyButton)
        val chooseDateLayout: LinearLayout = view.findViewById(R.id.llChooseRangeDateLayout)
        val tvStartDate: TextView = view.findViewById(R.id.tvStartDateRangeSheetLayout)
        val tvEndDate: TextView = view.findViewById(R.id.tvEndDateRangeSheetLayout)

        // Function to handle RadioButton click
        fun onRadioButtonClicked(checkedRadioButton: RadioButton) {
            rbTodayDateRange.isChecked = false
            rb7DaysDateRange.isChecked = false
            rb30DaysDateRange.isChecked = false
            rbChooseDateRange.isChecked = false
            checkedRadioButton.isChecked = true

            applyButton.isEnabled = true
            applyButton.setBackgroundColor(resources.getColor(R.color.primary))
            applyButton.setTextColor(resources.getColor(R.color.white))
            // Show or hide custom date range layout
            chooseDateLayout.visibility = if (checkedRadioButton.id == R.id.rbChooseDateRange) View.VISIBLE else View.GONE
        }

        rbTodayDateRange.setOnClickListener { onRadioButtonClicked(rbTodayDateRange) }
        rb7DaysDateRange.setOnClickListener { onRadioButtonClicked(rb7DaysDateRange) }
        rb30DaysDateRange.setOnClickListener { onRadioButtonClicked(rb30DaysDateRange) }
        rbChooseDateRange.setOnClickListener { onRadioButtonClicked(rbChooseDateRange) }

        tvStartDate.setOnClickListener { showDatePickerDialog(true) }
        tvEndDate.setOnClickListener { showDatePickerDialog(false) }

        applyButton.setOnClickListener {
            val selectedDateRange = when {
                rbTodayDateRange.isChecked -> rbTodayDateRange.text.toString()
                rb7DaysDateRange.isChecked -> rb7DaysDateRange.text.toString()
                rb30DaysDateRange.isChecked -> rb30DaysDateRange.text.toString()
                rbChooseDateRange.isChecked -> rbChooseDateRange.text.toString()
                else -> ""
            }

            (parentFragment as? MutationFragment)?.updateDateRange(selectedDateRange)
            dismiss()
        }

        // Observe ViewModel to update UI
        viewModel.startDate.observe(viewLifecycleOwner) { date ->
            tvStartDate.text = date
        }

        viewModel.endDate.observe(viewLifecycleOwner) { date ->
            tvEndDate.text = date
        }
    }

    private fun showDatePickerDialog(isStartDate: Boolean) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                if (isStartDate) {
                    viewModel.setStartDate(calendar)
                } else {
                    viewModel.setEndDate(calendar)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}
