package com.example.vehiclecontacting.Web.DiscussController

import android.os.Parcel
import android.os.Parcelable

data class OwnerComment(
    val commentCounts: Int,
    val createTime: String,
    val deleted: Int,
    val description: String,
    val favorCounts: Int,
    val fromId: String,
    val likeCounts: Int,
    val number: String,
    val photo1: String,
    val photo2: String,
    val photo3: String,
    val scanCounts: Int,
    val sex: String,
    val title: String,
    val userPhoto: String,
    val username: String
)  : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeInt(commentCounts)
        p0?.writeString(createTime)
        p0?.writeInt(deleted)
        p0?.writeString(description)
        p0?.writeInt(favorCounts)
        p0?.writeString(fromId)
        p0?.writeInt(likeCounts)
        p0?.writeString(number)
        p0?.writeString(photo1)
        p0?.writeString(photo2)
        p0?.writeString(photo3)
        p0?.writeInt(scanCounts)
        p0?.writeString(sex)
        p0?.writeString(title)
        p0?.writeString(userPhoto)
        p0?.writeString(username)
    }

    companion object CREATOR: Parcelable.Creator<OwnerComment> {
        override fun createFromParcel(p0: Parcel?): OwnerComment {
            p0 !!
            return OwnerComment(
                p0.readInt(),
                p0.readString()?:"",
                p0.readInt(),
                p0.readString()?:"",
                p0.readInt(),
                p0.readString()?:"",
                p0.readInt(),
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readInt(),
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:""
            )
        }

        override fun newArray(p0: Int): Array<OwnerComment?> {
            return arrayOfNulls(p0)
        }
    }
}
