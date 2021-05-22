package com.example.vehiclecontacting.Widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.view_avator.view.*

class AvtView(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_avator, this)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AvtView)
        // 读取 src 元素中 drawable 图片
        avt_img.setImageDrawable(ta.getDrawable(0))
        ta.recycle()
        // 设置 vip 图标
        // 默认为 灰色 图标
        avt_vip.setImageResource(R.drawable.gw_vip)
    }

    fun setAvt(image: Drawable) {
        avt_img.setImageDrawable(image)
    }

    fun setAvt(url: String) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.bp_defaultavt)
            .error(R.drawable.bp_defaultavt)
            .into(avt_img)
    }

    fun setVip(status: Boolean) {
        avt_vip.setImageResource(
            if (status)
                R.drawable.yw_vip
            else
                R.drawable.gw_vip
        )
    }
}