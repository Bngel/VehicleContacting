package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_vehiclecard.view.*

class VehicleCardView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, avt: String, username: String, license: String, vip: Int): super(context) {
        vehicleCard_avt.setAvt(avt)
        vehicleCard_username.text = username
        vehicleCard_license.text = license
        vehicleCard_avt.setVip(vip > 0)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_vehiclecard, this)
    }
}