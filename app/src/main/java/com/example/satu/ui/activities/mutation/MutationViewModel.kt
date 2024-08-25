package com.example.satu.ui.activities.mutation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MutationViewModel : ViewModel() {

    private val _dateRange = MutableLiveData<String>()
    val dateRange: LiveData<String> get() = _dateRange

    private val _accountSource = MutableLiveData<String>()
    val accountSource: LiveData<String> get() = _accountSource

    private val _transactionType = MutableLiveData<String>()
    val transactionType: LiveData<String> get() = _transactionType

    private val _accountName = MutableLiveData<String>()
    val accountName: LiveData<String> get() = _accountName

    fun setDateRange(dateRange: String) {
        _dateRange.value = dateRange
    }

    fun setAccountSource(accountSource: String) {
        _accountSource.value = accountSource
    }

    fun setTransactionType(transactionType: String) {
        _transactionType.value = transactionType
    }

    fun setAccountName(accountName: String) {
        _accountName.value = accountName
    }
}
