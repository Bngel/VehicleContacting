package com.example.vehiclecontacting.Web.DiscussController

import android.os.Parcel
import android.os.Parcelable

data class FirstComment(
    val deleted: Int,
    val replyDescription: String,
    val replyId: String,
    val replyLikeCounts: Int,
    val replyNumber: String,
    val replyPhoto: String,
    val replyTime: String,
    val replyUsername: String,
    val replyVip: Int
): Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeInt(deleted)
        p0?.writeString(replyDescription)
        p0?.writeString(replyId)
        p0?.writeInt(replyLikeCounts)
        p0?.writeString(replyNumber)
        p0?.writeString(replyPhoto)
        p0?.writeString(replyTime)
        p0?.writeString(replyUsername)
        p0?.writeInt(replyVip)
    }

    companion object CREATOR: Parcelable.Creator<FirstComment> {
        override fun createFromParcel(p0: Parcel?): FirstComment {
            p0 !!
            return FirstComment(
                p0.readInt(),
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readInt(),
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readInt()
            )
        }

        override fun newArray(p0: Int): Array<FirstComment?> {
            return arrayOfNulls(p0)
        }
    }
}

