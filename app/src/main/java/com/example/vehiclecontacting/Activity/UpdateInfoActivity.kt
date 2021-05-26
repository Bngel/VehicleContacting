package com.example.vehiclecontacting.Activity

import android.os.Bundle
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.activity_update_info.*

class UpdateInfoActivity : BaseActivity() {
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