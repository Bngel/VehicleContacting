package com.example.vehiclecontacting.Repository

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.vehiclecontacting.Activity.BaseActivity
import com.example.vehiclecontacting.Activity.MainActivity


object ActivityCollector {

    const val ACTIVITY_MAIN = 0x00
    const val ACTIVITY_CODE = 0x01
    const val ACTIVITY_LOGIN = 0x02
    const val CODE_GALLERY = 0x03
    const val CROP_PHOTO = 0x04 // 裁剪图片标识请求码
    const val ACTIVITY_DISCUSS = 0x05
    const val ACTIVITY_CREATE = 0x06
    const val ACTIVITY_DETAIL = 0x07
    const val ACTIVITY_EDIT = 0x08
    const val ACTIVITY_FOLLOW = 0x09
    const val ACTIVITY_FANS = 0x09
    const val ACTIVITY_CHANGE_INFO = 0x10
    const val ACTIVITY_SETTING = 0x11
    const val ACTIVITY_ADMIN = 0x12
    const val ACTIVITY_LICENSE = 0x13
    const val ACTIVITY_FROZEN = 0x14
    const val ACTIVITY_ADD_VEHICLE = 0x15
    const val ACTIVITY_MY_VEHICLE = 0x16
    const val ACTIVITY_MY_CREATE = 0x17
    const val ACTIVITY_MY_FAVOR = 0x18
    const val ACTIVITY_MY_HISTORY = 0x19
    const val ACTIVITY_FRIENDS = 0x20
    const val ACTIVITY_QRCODE = 0x21
    const val ACTIVITY_SEARCH = 0x22
    const val ACTIVITY_RELATION = 0x23
    const val ACTIVITY_CHAT = 0x24
    const val ACTIVITY_CHAT_BOX = 0x25

    var curActivity: BaseActivity? = null

    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun onlyActivity(only: Activity) {
        for (activity in activities) {
            if (!activity.isFinishing && activity != only) {
                activity.finish()
            }
        }
        activities.clear()
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }
}