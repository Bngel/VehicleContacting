package com.example.vehiclecontacting

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.widget.TextView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

object WidgetSetting {
    fun setFont(context: Context, view: TextView) {
        val mtypeface = Typeface.createFromAsset(context.assets, "font/HGHP_CNKI.TTF")
        view.setTypeface(mtypeface)
    }

    fun setFonts(context: Context, views: List<TextView>) {
        val mtypeface = Typeface.createFromAsset(context.assets, "font/HGHP_CNKI.TTF")
        for (view in views) {
            view.setTypeface(mtypeface)
        }
    }

    fun getMD5(string: String): String {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        var md5: MessageDigest? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
            val bytes = md5.digest(string.toByteArray())
            var result = ""
            for (b in bytes) {
                var temp = Integer.toHexString((b and 127).toInt())
                if (temp.length == 1) {
                    temp = "0$temp"
                }
                result += temp
            }
            if (result.length < 15)
                return result
            else
                return result.substring(0,15)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

}