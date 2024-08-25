package com.example.satu.ui.activities.mutation


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.satu.databinding.FragmentMutationResultBinding
import com.example.satu.ui.MainActivity

// import com.example.satu.ui.activities.mutation.network.Config

class MutationResultFragment : Fragment() {

    private var _binding: FragmentMutationResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var mutationViewModel: MutationResultViewModel
    private lateinit var mutationAdapter: MutationResultAdapter

    private val REQUEST_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMutationResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mutationViewModel = ViewModelProvider(this).get(MutationResultViewModel::class.java)
        mutationAdapter = MutationResultAdapter()
        binding.rvMutationHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mutationAdapter
        }
        // Retrieve the values from SharedPreferences for checking
        val sharedPreferences = requireActivity().getSharedPreferences("MutationPrefs", Context.MODE_PRIVATE)
        val dateRangeSelected = sharedPreferences.getString("dateRangeSelected", "")
        val transactionTypeSelected = sharedPreferences.getString("transactionTypeSelected", "")
        val accountNameSelected = sharedPreferences.getString("accountNameSelected", "")
        val accountSourceSelected = sharedPreferences.getString("accountSourceSelected", "")
        val accountSourceNumberSelected = sharedPreferences.getString("accountSourceNumberSelected", "")

        // Set the values to the TextViews in the CardView
        binding.tvMutationAccountNumber.text = accountSourceNumberSelected
        binding.tvMutationAccountName.text = accountNameSelected
        binding.tvMutationAccountType.text = accountSourceSelected
        binding.tvMutationAccountPeriode.text = dateRangeSelected
        binding.tvMutationAccountTransactionType.text = transactionTypeSelected

        binding.tvDonwload.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
            } else {
                downloadPdf()
            }
        }

        binding.tvHome.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Map transactionTypeSelected to API value
        val apiTransactionType = when (transactionTypeSelected) {
            "Transaksi keluar" -> "TRANSAKSI_KELUAR"
            "Transaksi masuk" -> "TRANSAKSI_MASUK"
            "Semua" -> "SEMUA"
            else -> "SEMUA" // Default value if not recognized
        }

        // Load mutation history with mapped transaction type
        val sharedPrefToken = requireActivity().getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        val token = sharedPrefToken .getString("token", "")
       val bearerToken = "Bearer $token"
        // val bearerToken = Config.getBearerToken()
        mutationViewModel.loadMutationHistory(
            bearerToken,
            accountSourceNumberSelected ?: "",  // Use default empty string if null
            dateRangeSelected ?: "",  // Use default empty string if null
            apiTransactionType
        )
        mutationViewModel.getMutationHistory().observe(viewLifecycleOwner) { mutationList ->
            mutationAdapter.submitList(mutationList)
            if (mutationList.isEmpty()) {
                Toast.makeText(requireContext(), "No mutation history available", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun downloadPdf() {
        val pdfGenerator = PdfGenerator()
        val success = pdfGenerator.generatePdfFromView(requireContext())
        if (success) {
            Toast.makeText(requireContext(), "PDF Downloaded", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Failed to generate PDF", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadPdf()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
