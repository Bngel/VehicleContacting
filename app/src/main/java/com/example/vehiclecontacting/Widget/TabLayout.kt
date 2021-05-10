package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.R

class TabLayout(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_tab, this)
    }
}