package com.example.satu.ui.activities.mutation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.View

class MutationHistoryView(context: Context) : View(context) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw header
        paint.color = Color.BLACK
        paint.textSize = 40f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("MUTASI REKENING", 200f, 100f, paint)

        // Draw account details
        paint.textSize = 30f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Nama: Karina Atifah Hana", 50f, 200f, paint)
        canvas.drawText("Nomor Rekening: 12991213145 (Saver+)", 50f, 250f, paint)
        canvas.drawText("Periode: Juli 2024", 50f, 300f, paint)
        canvas.drawText("Jenis Transaksi: Semua", 50f, 350f, paint)

        // Draw table headers
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 28f
        canvas.drawText("Tanggal", 50f, 450f, paint)
        canvas.drawText("Transaksi", 250f, 450f, paint)
        canvas.drawText("Keluar (IDR)", 650f, 450f, paint)
        canvas.drawText("Masuk (IDR)", 900f, 450f, paint)

        // Draw table content
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        val data = listOf(
            arrayOf("01 JUL", "Transfer masuk dari AFFA 310391020183", "", "200,000.00"),
            arrayOf("10 JUL", "QRIS 9102.99/11 ke CENTRE MART 10 Juli", "35,000.00", "")
        )

        var yPos = 500f
        for (row in data) {
            canvas.drawText(row[0], 50f, yPos, paint)
            canvas.drawText(row[1], 250f, yPos, paint)
            canvas.drawText(row[2], 650f, yPos, paint)
            canvas.drawText(row[3], 900f, yPos, paint)
            yPos += 50f
        }
    }
}
