package com.example.vehiclecontacting.Activity

import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Widget.ToastView

class QRScanActivity : BaseActivity(), QRCodeReaderView.OnQRCodeReadListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_scan)

        val qrCodeReaderView = findViewById<QRCodeReaderView>(R.id.scan_qrcode);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);
        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);
        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);
        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();
        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        ToastView(this).show(text?:"")
    }
}