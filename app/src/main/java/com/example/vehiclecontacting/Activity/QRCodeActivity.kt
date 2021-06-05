package com.example.vehiclecontacting.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.QRCodeUtil
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Web.WebService
import kotlinx.android.synthetic.main.activity_q_r_code.*

class QRCodeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_code)
        initWidget()
        initData()
    }

    private fun initData() {
        if (InfoRepository.loginStatus.status) {
            InfoRepository.user!!.apply {
                qrCode_username.text = username
                qrCode_avt.setAvt(photo)
                qrCode_avt.setVip(false)
                qrCode_qrCode.setImageBitmap(QRCodeUtil.createQRCodeBitmap("VehicleContacting://$id", 256, 256))
            }
        }
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        qrCode_close.setOnClickListener {
            finish()
        }
    }
}