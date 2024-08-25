package com.example.satu.ui.activities.mutation

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfGenerator {

    fun generatePdfFromView(context: Context): Boolean {
        return try {
            // Create the custom view
            val contentView = MutationHistoryView(context)

            // Measure and layout the contentView
            val width = 1200
            val height = 800
            contentView.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
            )
            contentView.layout(0, 0, width, height)

            // Create a PDF document
            val document = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
            val page = document.startPage(pageInfo)

            // Draw the view onto the page
            contentView.draw(page.canvas)

            // Finish the page
            document.finishPage(page)

            // Write the PDF to a file using scoped storage
            val pdfPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.toString()
            val file = File(pdfPath, "mutation_history_graphic.pdf")
            document.writeTo(FileOutputStream(file))

            // Close the document
            document.close()

            true // PDF generation success
        } catch (e: IOException) {
            e.printStackTrace()
            false // PDF generation failed
        }
    }
}
