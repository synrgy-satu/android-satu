package com.example.satu.ui.activities.mutation

import android.app.DatePickerDialog
import android.os.Build
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
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateRangeBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: DateRangeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_date_range_layout, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rbTodayDateRange: RadioButton = view.findViewById(R.id.rbTodayDateRange)
        val rb7DaysDateRange: RadioButton = view.findViewById(R.id.rb7DaysDateRange)
        val rbCurrentMonth: RadioButton = view.findViewById(R.id.rbCurrentMonth)
        val rbCurrentMonthMin1: RadioButton = view.findViewById(R.id.rbCurrentMonthMin1)
        val rbCurrentMonthMin2: RadioButton = view.findViewById(R.id.rbCurrentMonthMin2)
        val rbChooseDateRange: RadioButton = view.findViewById(R.id.rbChooseDateRange)
        val applyButton: MaterialButton = view.findViewById(R.id.applyButton)
        val chooseDateLayout: LinearLayout = view.findViewById(R.id.llChooseRangeDateLayout)
        val tvStartDate: TextView = view.findViewById(R.id.tvStartDateRangeSheetLayout)
        val tvEndDate: TextView = view.findViewById(R.id.tvEndDateRangeSheetLayout)
        val tvTodayDateRange: TextView = view.findViewById(R.id.tvTodayDateRange)
        val tv7DaysDateRange: TextView = view.findViewById(R.id.tv7DaysDateRange)
        val tvCurrentMonth: TextView = view.findViewById(R.id.tvCurrentMonth)
        val tvCurrentMonthMin1: TextView = view.findViewById(R.id.tvCurrentMonthMin1)
        val tvCurrentMonthMin2: TextView = view.findViewById(R.id.tvCurrentMonthMin2)

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("id"))
        val currentMonth = currentDate.format(formatter)
        tvCurrentMonth.text = currentMonth
        val prevMonth = currentDate.minusMonths(1).format(formatter)
        tvCurrentMonthMin1.text = prevMonth
        val twoMonthsAgo = currentDate.minusMonths(2).format(formatter)
        tvCurrentMonthMin2.text = twoMonthsAgo
        // Function to handle RadioButton click
        fun onRadioButtonClicked(checkedRadioButton: RadioButton) {
            rbTodayDateRange.isChecked = false
            rb7DaysDateRange.isChecked = false
            rbCurrentMonth.isChecked = false
            rbCurrentMonthMin1.isChecked = false
            rbCurrentMonthMin2.isChecked = false
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
        rbCurrentMonth.setOnClickListener { onRadioButtonClicked(rbCurrentMonth) }
        rbCurrentMonthMin1.setOnClickListener { onRadioButtonClicked(rbCurrentMonthMin1) }
        rbCurrentMonthMin2.setOnClickListener { onRadioButtonClicked(rbCurrentMonthMin2) }
        rbChooseDateRange.setOnClickListener { onRadioButtonClicked(rbChooseDateRange) }

        tvStartDate.setOnClickListener { showDatePickerDialog(true) }
        tvEndDate.setOnClickListener { showDatePickerDialog(false) }

        applyButton.setOnClickListener {
            val selectedDateRange = when {
                rbTodayDateRange.isChecked -> tvTodayDateRange.text.toString()
                rb7DaysDateRange.isChecked -> tv7DaysDateRange.text.toString()
                rbCurrentMonth.isChecked -> tvCurrentMonth.text.toString()
                rbCurrentMonthMin1.isChecked -> tvCurrentMonthMin1.text.toString()
                rbCurrentMonthMin2.isChecked -> tvCurrentMonthMin2.text.toString()
                rbChooseDateRange.isChecked -> tvStartDate.text.toString()+" - "+tvEndDate.text.toString()
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

