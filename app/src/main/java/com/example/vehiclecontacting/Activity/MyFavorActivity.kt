package com.example.vehiclecontacting.Activity

import android.os.Bundle
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.activity_my_favor.*

class MyFavorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_favor)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        myFavor_close.setOnClickListener {
            finish()
        }
    }
}