package com.example.vehiclecontacting.Widget

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.dialog_addvehicle.view.*
import kotlinx.android.synthetic.main.dialog_vehicle.view.*

class AddVehicleDialogView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, title: String): super(context) {
        dialogAdd_title.text = title
        if (title == "车辆类型") {
            dialogAdd_edit.hint = "请输入车辆类型\n1：油车 2：电动汽车\n3：油电混合汽车 4：电动自行车"
            dialogAdd_edit.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_addvehicle, this)
    }

    fun getText(): String {
        return dialogAdd_edit.text.toString()
    }
}