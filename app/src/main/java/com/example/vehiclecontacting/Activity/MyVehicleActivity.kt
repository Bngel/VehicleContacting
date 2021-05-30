package com.example.vehiclecontacting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.VehicleController.VehicleRepository
import com.example.vehiclecontacting.Widget.MyVehicleCardView
import kotlinx.android.synthetic.main.activity_my_vehicle.*

class MyVehicleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_vehicle)
        initWidget()
        initData()
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

    private fun initData() {
        val status = VehicleRepository.getVehicleList(InfoRepository.user!!.id)
        if (status == StatusRepository.SUCCESS) {
            for (vehicle in VehicleRepository.vehicleList) {
                val view = MyVehicleCardView(this, vehicle.license, vehicle.vehicleBrand, vehicle.description)
                myVehicle_cards.addView(view)
            }
        }
    }
}