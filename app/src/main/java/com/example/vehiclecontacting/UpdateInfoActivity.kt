package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_update_info.*

class UpdateInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_info)
        initWidget()
    }

    private fun initWidget(){
        avtEvent()
        closeEvent()
    }

    private fun avtEvent() {
        updateInfo_avt.hideVip()
    }

    private fun closeEvent() {
        updateInfo_close.setOnClickListener {
            finish()
        }
    }
 }