package com.example.satu.mutation

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.MutationResultAdapter
import com.example.satu.R
import com.example.satu.databinding.FragmentMutationResultBinding
import com.example.satu.mutation.network.Config
import com.example.satu.mutation.network.RetrofitClient
import com.example.satu.profile.UserDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MutationResultFragment : Fragment() {

    private var _binding: FragmentMutationResultBinding? = null
    private val binding get() = _binding!!
    private val tempMutationList: MutableList<MutationData> = mutableListOf()

    private lateinit var mutationViewModel: MutationResultViewModel
    private lateinit var mutationAdapter: MutationResultAdapter
    private var userRekeningNumber: String? = null
    private var userJenisRekening: String? = null
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
        fetchUser()
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
                showWebViewPopup(requireContext())
            }
        }

        // Map transactionTypeSelected to API value
        val apiTransactionType = when (transactionTypeSelected) {
            "Transaksi keluar" -> "TRANSAKSI_KELUAR"
            "Transaksi masuk" -> "TRANSAKSI_MASUK"
            "Semua" -> "SEMUA"
            else -> "SEMUA" // Default value if not recognized
        }

        // Load mutation history with mapped transaction type
        val bearerToken = Config.getBearerToken()
        mutationViewModel.loadMutationHistory(
            bearerToken,
            accountSourceNumberSelected ?: "",  // Use default empty string if null
            dateRangeSelected ?: "",  // Use default empty string if null
            apiTransactionType
        )
        mutationViewModel.getMutationHistory().observe(viewLifecycleOwner) { mutationList ->
            mutationAdapter.submitList(mutationList)
            tempMutationList.clear()  // Clear the previous data
            tempMutationList.addAll(mutationList)  // Add new data to the temporary list

            if (mutationList.isEmpty()) {
                Toast.makeText(requireContext(), "No mutation history available", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun generateTableRows(mutationList: List<MutationData>): String {
        val stringBuilder = StringBuilder()
        val numberFormat = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

        for (mutation in mutationList) {
            val tanggal = mutation.createdDate
            val transaksi = mutation.note ?: ""
            val formattedAmount = numberFormat.format(mutation.amount.toDouble())

            val keluar = if (mutation.jenisTransaksi == "TRANSAKSI_KELUAR") {
                formattedAmount
            } else ""

            val masuk = if (mutation.jenisTransaksi == "TRANSAKSI_MASUK") {
                formattedAmount
            } else ""

            stringBuilder.append("""
    <tr>
        <td>$tanggal</td>
        <td>$transaksi</td>
        <td>$keluar</td>
        <td>$masuk</td>
    </tr>
    """)
        }

        return stringBuilder.toString()
    }

    private fun showWebViewPopup(context: Context) {
        // Create a dialog
        val dialog = Dialog(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.webview_dialog, null)
        dialog.setContentView(view)

        val webView = view.findViewById<WebView>(R.id.webview)
        val downloadButton = view.findViewById<Button>(R.id.download_button)
        val sharedPreferences = requireActivity().getSharedPreferences("MutationPrefs", Context.MODE_PRIVATE)
        val dateRangeSelected = sharedPreferences.getString("dateRangeSelected", "")
        val transactionTypeSelected = sharedPreferences.getString("transactionTypeSelected", "")
        val accountNameSelected = sharedPreferences.getString("accountNameSelected", "")
        val accountSourceSelected =   userRekeningNumber+" ("+userJenisRekening+")"
        val accountSourceNumberSelected = sharedPreferences.getString("accountSourceNumberSelected", "")
        // Generate table rows from the tempMutationList
        val tableRows = generateTableRows(tempMutationList)

        // Load the HTML content into the WebView
        val htmlContent = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mutasi Rekening</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            width: 800px; /* Fixed width for the container */
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            box-sizing: border-box;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header img {
            width: 100px;
        }
        .header h1 {
            font-size: 22px;
            margin: 0;
            color: #2f3192;
        }
        .header p {
            margin: 5px 0;
            color: #333;
        }
        .note {
            background: #ffefa1;
            padding: 10px;
            border-radius: 5px;
            margin: 20px 0;
            font-size: 12px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background: #2f3192;
            color: white;
        }
        .footer {
            font-size: 12px;
            color: #333;
            text-align: right;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div style="text-align: left;">
                <h1>MUTASI REKENING</h1>
                <p><strong>Nama:</strong> $accountNameSelected</p>
                 <p><strong>Nomor Rekening:</strong> $accountSourceSelected</p>
                <p><strong>Periode:</strong> $dateRangeSelected</p>
                <p><strong>Jenis Transaksi:</strong> $transactionTypeSelected</p>
            </div>
            <img src="https://i.ibb.co/S6QL8vK/your-logo.png" alt="Logo">
        </div>

        <div class="note">
            <strong>CATATAN:</strong> Apabila nasabah tidak melakukan sanggahan atas Laporan Mutasi Rekening ini sampai dengan akhir bulan berikutnya, nasabah dianggap telah menyetujui segala data yang tercantum pada Laporan Mutasi Rekening ini.
        </div>

        <table>
            <tr>
                <th>TANGGAL</th>
                <th>TRANSAKSI</th>
                <th>KELUAR (IDR)</th>
                <th>MASUK (IDR)</th>
            </tr>
            $tableRows
        </table>

        <div class="footer">
            Bank Satu, Jl. Binar Academy, No. 1<br>
            Call Satu 021-111-111
        </div>
    </div>
</body>
</html>
"""

        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        downloadButton.setOnClickListener {
            webView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val width = webView.measuredWidth
            val height = webView.measuredHeight

            generatePdfFromWebView(webView, width, height)
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun generatePdfFromWebView(webView: WebView, width: Int, height: Int) {
        // First, make sure WebView is laid out with its full content
        webView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        webView.layout(0, 0, webView.measuredWidth, webView.measuredHeight)

        // Create a Bitmap with the measured width and height
        val bitmap = Bitmap.createBitmap(webView.measuredWidth, webView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw WebView content onto the Bitmap
        webView.draw(canvas)

        // Create a PDF document
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(webView.measuredWidth, webView.measuredHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        // Draw the Bitmap onto the PDF page
        val pdfCanvas = page.canvas
        pdfCanvas.drawBitmap(bitmap, 0f, 0f, null)
        pdfDocument.finishPage(page)

        // Get the current date and time
        val currentDateTime = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

        // Create a file name with the current date and time
        val fileName = "mutasi_rekening_$currentDateTime.pdf"
        val directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(directoryPath, fileName)

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(requireContext(), "PDF saved to $file", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error saving PDF "+ e.printStackTrace(), Toast.LENGTH_SHORT).show()
        }

        pdfDocument.close()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showWebViewPopup(requireContext())
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchUser() {
        val token = Config.getBearerToken() // Your token
        val call = RetrofitClient.apiService.getUserData(token)

        call.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(
                call: Call<UserDataResponse>,
                response: Response<UserDataResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val userData = response.body()!!
                    // Update the UI with the fetched user data
                    userRekeningNumber = userData.data.rekenings[0].rekeningNumber.toString()
                    userJenisRekening = userData.data.rekenings[0].jenisRekening
                    // Update other views here
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(context, "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                // Handle error
                Toast.makeText(context, "Failed to fetch user data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}