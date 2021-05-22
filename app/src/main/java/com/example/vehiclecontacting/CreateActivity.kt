package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.view_createtitle.*

class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        create_send.setOnClickListener {
            Log.d(StatusRepository.VehicleLog, getEditData())
        }
    }

    private fun getEditData(): String {
        val editList = create_edit.buildEditData()
        val content = StringBuffer()
        for (itemData in editList) {
            if (itemData.inputStr != null)
                content.append(itemData.inputStr)
            else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
            }
        }
        return content.toString()
    }


}