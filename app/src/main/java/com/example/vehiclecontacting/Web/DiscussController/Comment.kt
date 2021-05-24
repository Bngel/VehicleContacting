package com.example.vehiclecontacting.Web.DiscussController

import android.os.Parcel
import android.os.Parcelable

data class Comment(
    val commentCounts: Int,
    val comments: String,
    val createTime: String,
    val id: String,
    val likeCounts: Int,
    val number: String,
    val replyDescription1: String,
    val replyDescription2: String,
    val replyDescription3: String,
    val replyId1: String,
    val replyId2: String,
    val replyId3: String,
    val replyNumber1: String,
    val replyNumber2: String,
    val replyNumber3: String,
    val replyUsername1: String,
    val replyUsername2: String,
    val replyUsername3: String,
    val secondReplyUsername1: String,
    val secondReplyUsername2: String,
    val secondReplyUsername3: String,
    val sex: String,
    val userPhoto: String,
    val username: String
)   : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeInt(commentCounts)
        p0?.writeString(comments)
        p0?.writeString(createTime)
        p0?.writeString(id)
        p0?.writeInt(likeCounts)
        p0?.writeString(number)
        p0?.writeString(replyDescription1)
        p0?.writeString(replyDescription2)
        p0?.writeString(replyDescription3)
        p0?.writeString(replyId1)
        p0?.writeString(replyId2)
        p0?.writeString(replyId3)
        p0?.writeString(replyNumber1)
        p0?.writeString(replyNumber2)
        p0?.writeString(replyNumber3)
        p0?.writeString(replyUsername1)
        p0?.writeString(replyUsername2)
        p0?.writeString(replyUsername3)
        p0?.writeString(secondReplyUsername1)
        p0?.writeString(secondReplyUsername2)
        p0?.writeString(secondReplyUsername3)
        p0?.writeString(sex)
        p0?.writeString(userPhoto)
        p0?.writeString(username)
    }

    companion object CREATOR: Parcelable.Creator<Comment> {
        override fun createFromParcel(p0: Parcel?): Comment {
            p0 !!
            return Comment(
                p0.readInt(),
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readInt(),
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:"",
                p0.readString()?:""
            )
        }

        override fun newArray(p0: Int): Array<Comment?> {
            return arrayOfNulls(p0)
        }
    }

}