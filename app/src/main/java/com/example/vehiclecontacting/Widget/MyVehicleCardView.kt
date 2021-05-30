package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_myvehiclecard.view.*

class MyVehicleCardView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, license: String?, model: String?, description: String?): super(context) {
        myVehicleCard_license.text = license?:""
        myVehicleCard_model.text = model?:""
        myVehicleCard_description.text = description?:""
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_myvehiclecard, this)
    }
}