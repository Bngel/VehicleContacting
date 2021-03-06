package com.example.vehiclecontacting.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.AdministratorController.AdministratorRepository
import com.example.vehiclecontacting.Web.UserController.User
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.AddFriendDialogView
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.view_userinfo.*

class UserDetailActivity : BaseActivity() {

    var isSelf = false
    lateinit var userId: String
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        initData()
        initWidget()
    }

    private fun initWidget() {
        if (isSelf)
            editEvent()
        else
            followEvent()
        closeEvent()
        forbidEvent()
        addFriendEvent()
        linkEvent()
        chatEvent()
    }

    private fun chatEvent() {
        if (InfoRepository.loginStatus.status) {
            detail_chat.visibility = View.VISIBLE
            detail_chat.setOnClickListener {
                val chatIntent = Intent(this, ChatActivity::class.java)
                chatIntent.putExtra("userId", userId)
                chatIntent.putExtra("userPhoto", user.photo)
                startActivityForResult(chatIntent, ActivityCollector.ACTIVITY_CHAT)
            }
        }
        else {
            detail_chat.visibility = View.GONE
        }
    }

    private fun linkEvent() {
        if (InfoRepository.loginStatus.status) {
            val linkStatus = UserRepository.getJudgeLink(InfoRepository.user!!.id, userId)
            if (linkStatus == StatusRepository.SUCCESS) {
                detail_link.visibility = View.VISIBLE
                detail_link.setOnClickListener {
                    val linkDialog = AlertDialog.Builder(this)
                        .setTitle("??????:")
                        .setMessage("?????????????????? ${user.username} ???????????????")
                        .setPositiveButton("??????",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                val linkStatus = UserRepository.postLinkUser(InfoRepository.user!!.id, "", userId)
                                if (linkStatus == StatusRepository.SUCCESS) {
                                    ToastView(this).show("???????????????????????????")
                                }
                                else
                                    ToastView(this).show("???????????????????????????")
                            })
                        .setNegativeButton("??????", null)
                        .create()
                    linkDialog.show()
                }
            }
            else {
                detail_link.visibility = View.GONE
            }
        }
        else {
            detail_link.visibility = View.GONE
        }
    }

    private fun addFriendEvent() {
        if (InfoRepository.loginStatus.status) {
            val status = UserRepository.postJudgeFriend(InfoRepository.user!!.id, userId)
            if (status == StatusRepository.SUCCESS) {
                if (UserRepository.isFriend == 0) {
                    detail_add.visibility = View.VISIBLE
                    detail_add.setOnClickListener {
                        val view = AddFriendDialogView(this, user.username)
                        val dialog = AlertDialog.Builder(this)
                            .setView(view)
                            .setPositiveButton("??????",
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    val reasonEdit = view.findViewById<EditText>(R.id.dialogFriend_edit)
                                    val addStatus = UserRepository.postFriend(InfoRepository.user!!.id, reasonEdit.text.toString()?:"", userId?:"")
                                    if (addStatus == StatusRepository.SUCCESS)
                                        ToastView(this).show("??????????????????")
                                    else
                                        ToastView(this).show("??????????????????")
                                })
                            .setNegativeButton("??????",null)
                            .create()
                        dialog.show()
                    }
                }
                else {
                    detail_add.visibility = View.GONE
                }
            }
        }
    }

    private fun forbidEvent() {
        if (InfoRepository.loginStatus.status) {
            if (InfoRepository.user!!.vip >= 100) {
                detail_forbid.visibility = View.VISIBLE
                detail_forbid.setOnClickListener {
                    val dialog = AlertDialog.Builder(this)
                        .setTitle("??????:")
                        .setMessage("?????????????????? ${user.username} 60???????")
                        .setPositiveButton("??????",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                val forbidStatus = AdministratorRepository.postFrozeUser(userId, 60) // ???????????????
                                if (forbidStatus == StatusRepository.SUCCESS)
                                    ToastView(this).show("?????? ${user.username} ??????(60??????)")
                                else {
                                    ToastView(this).show("?????? ${user.username} ??????")
                                }
                            })
                        .setNegativeButton("??????",
                            DialogInterface.OnClickListener { dialogInterface, i ->  })
                        .create()
                        .show()
                }
            }
            else {
                detail_forbid.visibility = View.GONE
            }
        }
    }

    private fun closeEvent(){
        detail_close.setOnClickListener {
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun followEvent() {
        if (InfoRepository.loginStatus.status) {
            detail_follow.visibility = View.VISIBLE
            val status = UserRepository.postJudgeFavor(InfoRepository.user!!.id, userId)
            if (status == StatusRepository.SUCCESS) {
                detail_follow.setStatus(UserRepository.followStatus)
                detail_follow.cardFollow(userId)
                detail_follow.isClickable = true
            }
            else {
                detail_follow.setStatus(UserRepository.FOLLOW_NOT)
                detail_follow.isClickable = false
            }
        }
    }

    private fun editEvent() {
        detail_edit.visibility = View.VISIBLE
        detail_edit.setOnClickListener {
            val editIntent = Intent(this, UpdateInfoActivity::class.java)
            startActivityForResult(editIntent, ActivityCollector.ACTIVITY_EDIT)
        }
    }

    private fun initData() {
        isSelf = intent.getBooleanExtra("isSelf", false)
        userId = intent.getStringExtra("id")?:""
        user = UserRepository.getUserById(userId)
        if (user.id != "") {
            detail_avt.setAvt(user.photo)
            detail_momentsCount.text = user.momentCounts.toString()
            detail_followCount.text = user.followCounts.toString()
            detail_fansCount.text = user.fansCounts.toString()
            detail_username.text = user.username
            when {
                user.vip <= 0 -> {
                    detail_vip.text ="?????????VIP??????"
                }
                user.vip < 100 -> {
                    detail_vip.text ="vip${user.vip}"
                }
                else -> {
                    detail_vip.text ="???????????????"
                }
            }
        }
    }
}