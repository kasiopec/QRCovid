package com.kasiopec.qrcovid

import android.content.Context
import android.graphics.Bitmap
import android.util.TypedValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.journeyapps.barcodescanner.BarcodeEncoder

object QRView {
    private val barcodeEncoder = BarcodeEncoder()

    /**
     * Generates QR code bitmap image from a passed string
     * **/
    fun generateQrImage(covidString: String) : ImageBitmap {
        val bitmap = barcodeEncoder.encodeBitmap(covidString, BarcodeFormat.QR_CODE, 400, 400)
        return bitmap.asImageBitmap()
    }

    /**
     * Method that reads bitmap image and tried to decode a string, once string is obtained recreates
     * a new bitmap image.
     * **/
    fun recreateQrCodeFromBitmap(context: Context, bitmap: Bitmap) :ImageBitmap{
        val intArray = IntArray(bitmap.width * bitmap.height)
        //copy pixel data from the Bitmap into the 'intArray' array
        bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val luminanceSource = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(luminanceSource))
        val qrCodeResult = MultiFormatReader().decode(binaryBitmap)
        val newQrBitmap = barcodeEncoder.encodeBitmap(qrCodeResult.text, BarcodeFormat.QR_CODE, 600.dpToPx(context), 600.dpToPx(context))
        return newQrBitmap.asImageBitmap()
    }

    private fun Number.dpToPx(context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}