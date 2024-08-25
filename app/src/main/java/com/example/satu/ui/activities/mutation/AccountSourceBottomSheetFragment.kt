package com.example.satu.ui.activities.mutation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.satu.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AccountSourceBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: AccountSourceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_account_source_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvSourceAccounts)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.setContext(requireContext()) // Pass the Context
        viewModel.accounts.observe(viewLifecycleOwner) { accounts ->
            val adapter = AccountSourceAdapter(accounts) { account ->
                // Handle item click
                // For example, update the TextView in the Fragment
                (parentFragment as? MutationFragment)?.updateAccountSource(account)
                dismiss()
            }
            recyclerView.adapter = adapter
        }
    }
}
