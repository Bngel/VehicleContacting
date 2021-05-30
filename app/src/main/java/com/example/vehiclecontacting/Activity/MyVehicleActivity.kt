package com.example.vehiclecontacting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import kotlinx.android.synthetic.main.activity_my_vehicle.*

class MyVehicleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_vehicle)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        addEvent()
    }

    private fun closeEvent() {
        myVehicle_close.setOnClickListener {
            finish()
        }
    }

    private fun addEvent() {
        myVehicle_add.setOnClickListener {
            val addIntent = Intent(this, AddVehicleActivity::class.java)
            startActivityForResult(addIntent, ActivityCollector.ACTIVITY_ADD_VEHICLE)
        }
    }
}