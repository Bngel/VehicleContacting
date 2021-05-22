package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.vehiclecontacting.R

class CreateTitleView(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_createtitle, this)
    }
}