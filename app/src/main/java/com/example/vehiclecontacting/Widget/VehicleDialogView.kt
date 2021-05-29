package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.dialog_vehicle.view.*

class VehicleDialogView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, img: String): super(context) {
        Glide.with(context)
            .load(img)
            .into(vehicleDialog_img)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_vehicle, this)
    }
}