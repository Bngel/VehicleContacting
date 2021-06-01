package com.example.vehiclecontacting.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.activity_my_history.*

class MyHistoryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_history)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        myHistory_close.setOnClickListener {
            finish()
        }
    }
}