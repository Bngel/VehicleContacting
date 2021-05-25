package com.example.vehiclecontacting.Web.DiscussController

import android.os.Parcel
import android.os.Parcelable

data class OwnerComment(
    var commentCounts: Int,
    var createTime: String,
    var deleted: Int,
    var description: String,
    var favorCounts: Int,
    var fromId: String,
    var likeCounts: Int,
    var number: String,
    var photo1: String,
    var photo2: String,
    var photo3: String,
    var scanCounts: Int,
    var sex: String,
    var title: String,
    var userPhoto: String,
    var username: String
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
