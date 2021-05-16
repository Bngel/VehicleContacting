package com.example.vehiclecontacting.Widget

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.vehiclecontacting.R


class ToastView(val context: Context) {

    fun show(msg: String) {
        show(msg, Toast.LENGTH_SHORT)
    }

    fun showLong(msg: String) {
        show(msg, Toast.LENGTH_LONG)
    }

    private fun show(massage: String, showLength: Int) {
        //使用布局加载器，将编写的toast_layout布局加载进来
        val view: View = LayoutInflater.from(context).inflate(R.layout.view_toast, null)
        //获取TextView
        val title = view.findViewById(R.id.toast_tv) as TextView
        //设置显示的内容
        title.text = massage
        val toast = Toast(context)
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, 100)
        //设置显示时间
        toast.duration = showLength
        toast.view = view
        toast.show()
    }

}