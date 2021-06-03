package com.example.vehiclecontacting.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.*
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.view_friendapplycard.view.*

class FriendsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        initWidget()
        initData()
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        friends_close.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        if (InfoRepository.loginStatus.status) {
            val friendStatus = UserRepository.getFriendList(InfoRepository.user!!.friendCounts,
                InfoRepository.user!!.id,  1, 1)
            if (friendStatus == StatusRepository.SUCCESS) {
                for (friend in UserRepository.friendList) {
                    val view = UserCardView(this, friend.id, friend.photo, friend.username, friend.introduction?:"")
                    val followBtn = view.findViewById<FollowView>(R.id.userCard_follow)
                    val followStatus = UserRepository.postJudgeFavor(InfoRepository.user!!.id, friend.id)
                    followBtn.setStatus(if (followStatus == StatusRepository.SUCCESS) UserRepository.followStatus else UserRepository.FOLLOW_NOT)
                    followBtn.cardFollow(friend.id)
                    view.setOnClickListener {
                        TODO (" connect with others ")
                    }
                    view.setOnLongClickListener {
                        val deleteDialog = AlertDialog.Builder(this)
                            .setTitle("提示:")
                            .setMessage("是否确定删除好友: ${friend.username} ?")
                            .setPositiveButton("确定",
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    val deleteStatus = UserRepository.deleteFriend(InfoRepository.user!!.id, friend.id)
                                    if (deleteStatus == StatusRepository.SUCCESS) {
                                        friends_cards.removeAllViews()
                                        initData()
                                        ToastView(this).show("删除好友成功")
                                    }
                                    else {
                                        ToastView(this).show("删除好友失败")
                                    }
                                })
                            .setNegativeButton("取消", null)
                            .create()
                            .show()
                        true
                    }
                    friends_cards.addView(view)
                }
            }
            val applyListStatus = UserRepository.getPostFriendList(1000, InfoRepository.user!!.id, 1)
            if (applyListStatus == StatusRepository.SUCCESS) {
                val dealingList = UserRepository.friendApplyList.filter { it.isPass == 0 } as ArrayList
                val count = dealingList.count()
                if (count > 0) {
                    friends_attention.visibility = View.VISIBLE
                    friends_attention.text = if (count > 99) "99+" else count.toString()
                }
                else {
                    friends_attention.visibility = View.INVISIBLE
                }
                friends_apply.setOnClickListener {
                    val contentView = LayoutInflater.from(this).inflate(R.layout.popup_friendapply, null)
                    val popWindow = PopupWindow(
                        contentView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true
                    )
                    popWindow.contentView = contentView
                    val applyCards = contentView.findViewById<LinearLayout>(R.id.friendapply_cards)
                    val applyRefresh = contentView.findViewById<com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout>(
                        R.id.friendapply_refresh
                    )
                    applyRefresh.setAutoLoadMore(true)
                    applyRefresh.setEnableRefresh(false)
                    applyRefresh.setEnableOverScroll(false)
                    applyRefresh.setEnableLoadmore(false)


                    for (applyFriend in UserRepository.friendApplyList) {
                        if (applyFriend.isPass == 0) {
                            val view = FriendApplyCardView(
                                this,
                                applyFriend.id,
                                applyFriend.photo,
                                applyFriend.username,
                                applyFriend.reason
                            )
                            val agreeBtn = view.findViewById<ImageView>(R.id.friendapplyCard_agree)
                            agreeBtn.setOnClickListener {
                                val status = UserRepository.postVerifyFriend(applyFriend.id, 1, InfoRepository.user!!.id)
                                if (status == StatusRepository.SUCCESS) {
                                    ToastView(this).show("添加好友成功")
                                    applyCards.removeView(view)
                                    initData()
                                }
                                else
                                    ToastView(this).show("添加好友失败")
                            }
                            val refuseBtn = view.findViewById<ImageView>(R.id.friendapplyCard_refuse)
                            refuseBtn.setOnClickListener {
                                val status = UserRepository.postVerifyFriend(applyFriend.id, 2, InfoRepository.user!!.id)
                                if (status == StatusRepository.SUCCESS) {
                                    ToastView(this).show("拒绝成功")
                                    applyCards.removeView(view)
                                    initData()
                                }
                                else
                                    ToastView(this).show("拒绝失败")
                            }
                            applyCards.addView(view)
                        }
                    }

                    val applyClose = contentView.findViewById<ImageView>(R.id.friendapply_close)
                    applyClose.setOnClickListener {
                        popWindow.dismiss()
                        initData()
                    }

                    val rootView = LayoutInflater.from(this).inflate(R.layout.activity_friends, null)
                    popWindow.animationStyle = R.style.contextCommentAnim
                    popWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
                }
            }
        }
    }


}