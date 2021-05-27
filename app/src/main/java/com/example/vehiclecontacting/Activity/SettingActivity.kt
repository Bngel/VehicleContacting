package com.example.vehiclecontacting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initWidget()
    }

    private fun initWidget() {
        quitEvent()
    }

    private fun quitEvent() {
        setting_quit.setOnClickListener {
            val intent = Intent()
            intent.putExtra("quit", true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}