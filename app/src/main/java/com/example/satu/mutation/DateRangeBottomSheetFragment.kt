package com.example.satu.mutation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.satu.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.satu.callbacks.DateRangeCallback
import java.text.SimpleDateFormat
import java.util.*

class DateRangeBottomSheetFragment : BottomSheetDialogFragment() {

    private val DATE_RANGE_KEY = "dateRangeKey"
    private val textViewMap = mutableMapOf<Int, TextView>()
    private val dynamicRadioButtons = mutableListOf<RadioButton>()
    private var selectedDateRange: String? = null
    private var callback: DateRangeCallback? = null

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
        val radioGroupDateRange: RadioGroup = view.findViewById(R.id.radioGroupDateRange)
        val tvTodayPeriode: TextView = view.findViewById(R.id.tvTodayPeriode)
        val tv7DaysPeriode: TextView = view.findViewById(R.id.tv7DaysPeriode)
        val tv30DaysPeriode: TextView = view.findViewById(R.id.tv30DaysPeriode)
        val applyButton: MaterialButton = view.findViewById(R.id.applyButton)

        fun onRadioButtonClicked(checkedRadioButton: RadioButton) {
            rbTodayDateRange.isChecked = false
            rb7DaysDateRange.isChecked = false
            rb30DaysDateRange.isChecked = false

            for (radioButton in dynamicRadioButtons) {
                radioButton.isChecked = false
            }

            checkedRadioButton.isChecked = true

            applyButton.isEnabled = true
            applyButton.setBackgroundColor(resources.getColor(R.color.primary))
            applyButton.setTextColor(resources.getColor(R.color.white))

            selectedDateRange = textViewMap[checkedRadioButton.id]?.text.toString()
        }

        rbTodayDateRange.setOnClickListener { onRadioButtonClicked(rbTodayDateRange) }
        rb7DaysDateRange.setOnClickListener { onRadioButtonClicked(rb7DaysDateRange) }
        rb30DaysDateRange.setOnClickListener { onRadioButtonClicked(rb30DaysDateRange) }

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

        for (i in 0..5) {
            val monthYear = dateFormat.format(calendar.time)

            val constraintLayout = ConstraintLayout(requireContext()).apply {
                id = View.generateViewId()
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 10.dpToPx())
                }
            }

            val textView = TextView(requireContext()).apply {
                id = View.generateViewId()
                text = monthYear
                setTextColor(resources.getColor(R.color.black))
                textSize = 14f
                layoutParams = ConstraintLayout.LayoutParams(
                    0,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginStart = 16.dpToPx()
                    marginEnd = 10.dpToPx()
                }
                gravity = android.view.Gravity.START or android.view.Gravity.CENTER_VERTICAL
            }

            val radioButton = RadioButton(requireContext()).apply {
                id = View.generateViewId()
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = 15.dpToPx()
                }
                gravity = android.view.Gravity.END or android.view.Gravity.CENTER_VERTICAL
            }

            radioButton.setOnClickListener { onRadioButtonClicked(radioButton) }

            dynamicRadioButtons.add(radioButton)

            constraintLayout.addView(textView)
            constraintLayout.addView(radioButton)

            textViewMap[radioButton.id] = textView

            ConstraintSet().apply {
                clone(constraintLayout)
                connect(textView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                connect(textView.id, ConstraintSet.END, radioButton.id, ConstraintSet.START)
                connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

                connect(radioButton.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                connect(radioButton.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                connect(radioButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

                applyTo(constraintLayout)
            }

            radioGroupDateRange.addView(constraintLayout)

            calendar.add(Calendar.MONTH, -1)
        }

        applyButton.setOnClickListener {
            val dateRange = when {
                rbTodayDateRange.isChecked -> tvTodayPeriode.text.toString()
                rb7DaysDateRange.isChecked -> tv7DaysPeriode.text.toString()
                rb30DaysDateRange.isChecked -> tv30DaysPeriode.text.toString()
                else -> selectedDateRange
            }

            if (dateRange != null) {
                callback?.onDateRangeSelected(dateRange) // Notify the callback
                dismiss()
            } else {
                Toast.makeText(context, "Pilih periode terlebih dahulu", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setDateRangeCallback(callback: DateRangeCallback) {
        this.callback = callback
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
