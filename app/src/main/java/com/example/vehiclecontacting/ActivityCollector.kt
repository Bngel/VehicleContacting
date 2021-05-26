package com.example.vehiclecontacting

import android.app.Activity

object ActivityCollector {

    const val ACTIVITY_CODE = 0x01
    const val ACTIVITY_LOGIN = 0x02
    const val CODE_GALLERY = 0x03
    const val CROP_PHOTO = 0x04 // 裁剪图片标识请求码
    const val ACTIVITY_DISCUSS = 0x05
    const val ACTIVITY_CREATE = 0x06
    const val ACTIVITY_DETAIL = 0x07

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