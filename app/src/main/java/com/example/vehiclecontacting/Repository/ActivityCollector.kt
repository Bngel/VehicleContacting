package com.example.vehiclecontacting.Repository

import android.app.Activity

object ActivityCollector {

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