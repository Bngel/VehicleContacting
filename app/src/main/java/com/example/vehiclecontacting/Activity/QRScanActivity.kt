package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Widget.ToastView
import java.util.regex.Pattern

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
        val pattern = Pattern.compile("^VehicleContacting://(.+)")
        val result = pattern.matcher(text ?: "")
        if (result.find()) {
            val id = result.group(1)
            val detailIntent = Intent(this, UserDetailActivity::class.java)
            if (InfoRepository.loginStatus.status)
                detailIntent.putExtra("isSelf", id == InfoRepository.user!!.id)
            else
                detailIntent.putExtra("isSelf", false)
            detailIntent.putExtra("id", id)
            startActivityForResult(detailIntent, ActivityCollector.ACTIVITY_DETAIL)
        }
        else {
            ToastView(this).show("解析失败")
        }
    }

    override fun onResume() {
        super.onResume()
        val qrCodeReaderView = findViewById<QRCodeReaderView>(R.id.scan_qrcode);
        qrCodeReaderView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        val qrCodeReaderView = findViewById<QRCodeReaderView>(R.id.scan_qrcode);
        qrCodeReaderView.stopCamera()
    }
}