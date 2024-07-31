package com.example.satu.mutation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountSourceViewModel : ViewModel() {

    private val _accounts = MutableLiveData<List<AccountSource>>()
    val accounts: LiveData<List<AccountSource>> get() = _accounts

    init {
        // Create dummy data
        _accounts.value = listOf(
            AccountSource("1000 8310 4396 312", "Account A"),
            AccountSource("2000 9210 4396 413", "Account B")
        )
    }
}
