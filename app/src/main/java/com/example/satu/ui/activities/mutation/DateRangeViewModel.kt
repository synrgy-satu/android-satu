package com.example.satu.ui.activities.mutation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class DateRangeViewModel : ViewModel() {

    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> get() = _endDate

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    fun setStartDate(calendar: Calendar) {
        _startDate.value = dateFormat.format(calendar.time)
    }

    fun setEndDate(calendar: Calendar) {
        _endDate.value = dateFormat.format(calendar.time)
    }
}
