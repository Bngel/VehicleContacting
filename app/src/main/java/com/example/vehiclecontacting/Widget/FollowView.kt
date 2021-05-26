package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.vehiclecontacting.InfoRepository
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import kotlinx.android.synthetic.main.activity_discuss.*
import kotlinx.android.synthetic.main.view_follow.view.*

class FollowView: LinearLayout {

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context): super(context)

    var followStatus = UserRepository.FOLLOW_NOT

    init {
        LayoutInflater.from(context).inflate(R.layout.view_follow, this)
    }

    fun setStatus(status: Int) {
        followStatus = status
    }

    fun follow() {

        if (UserRepository.followStatus == UserRepository.FOLLOW_NOT) {
            follow_btn.text = resources.getString(R.string.fans_follow)
            follow_btn.background = resources.getDrawable(R.drawable.bk_followbtn)
            follow_btn.setTextColor(resources.getColor(R.color.colorFollow))
        }
        else {
            follow_btn.text = resources.getString(R.string.fans_followed)
            follow_btn.background = resources.getDrawable(R.drawable.bk_followedbtn)
            follow_btn.setTextColor(resources.getColor(R.color.colorFollowed))
        }
        follow_btn.setOnClickListener {
            if (UserRepository.followStatus == UserRepository.FOLLOW_NOT) {
                val follow = UserRepository.postFans(InfoRepository.user!!.id, DiscussRepository.ownerComment.fromId)
                if (follow == StatusRepository.SUCCESS) {
                    follow_btn.text = resources.getString(R.string.fans_followed)
                    follow_btn.background = resources.getDrawable(R.drawable.bk_followedbtn)
                    follow_btn.setTextColor(resources.getColor(R.color.colorFollowed))
                    UserRepository.followStatus = UserRepository.FOLLOW_ED
                    ToastView(context).show("关注成功")
                }
                else {
                    ToastView(context).show("关注失败, 请稍后重试")
                }
            }
            else {
                val follow = UserRepository.deleteFans(InfoRepository.user!!.id, DiscussRepository.ownerComment.fromId)
                if (follow == StatusRepository.SUCCESS) {
                    follow_btn.text = resources.getString(R.string.fans_follow)
                    follow_btn.background = resources.getDrawable(R.drawable.bk_followbtn)
                    follow_btn.setTextColor(resources.getColor(R.color.colorFollow))
                    UserRepository.followStatus = UserRepository.FOLLOW_NOT
                    ToastView(context).show("取消关注成功")
                }
                else {
                    ToastView(context).show("取消关注失败, 请稍后重试")
                }
            }
        }
    }
}