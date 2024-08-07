package com.example.satu.mutation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DatePeriodeViewModel : ViewModel() {

    // LiveData for selected date range
    private val _selectedDateRange = MutableLiveData<String>()
    val selectedDateRange: LiveData<String> get() = _selectedDateRange

    // Function to update the selected date range
    fun setSelectedDateRange(dateRange: String) {
        _selectedDateRange.value = dateRange
    }
}
