package com.example.vehiclecontacting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        licenseEvent()
        frozeEvent()
    }

    private fun closeEvent() {
        admin_close.setOnClickListener {
            finish()
        }
    }

    private fun licenseEvent() {
        admin_license.setOnClickListener {
            val licenseIntent = Intent(this, LicenseActivity::class.java)
            startActivityForResult(licenseIntent, ActivityCollector.ACTIVITY_LICENSE)
        }
    }

    private fun frozeEvent() {
        admin_frozen.setOnClickListener {
            val frozenIntent = Intent(this, FrozeActivity::class.java)
            startActivityForResult(frozenIntent, ActivityCollector.ACTIVITY_FROZEN)
        }
    }
}