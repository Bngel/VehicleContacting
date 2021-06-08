package com.example.vehiclecontacting.Activity

import android.app.ActionBar
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.view.marginTop
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.FriendApplyCardView
import com.example.vehiclecontacting.Widget.ToastView
import com.example.vehiclecontacting.Widget.UserCardView
import kotlinx.android.synthetic.main.activity_relation.*

class RelationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relation)
        initData()
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        applyEvent()
    }

    private fun applyEvent() {

    }

    private fun closeEvent() {
        relation_close.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        relation_infos.removeAllViews()
        if (InfoRepository.loginStatus.status) {
            val relationStatus = UserRepository.getLinkUser(InfoRepository.user!!.id)
            if (relationStatus == StatusRepository.SUCCESS) {
                for (relation in UserRepository.linkList) {
                    val licenses = ArrayList<String>()
                    if (relation.license1 != null)
                        licenses.add(relation.license1)
                    if (relation.license2 != null)
                        licenses.add(relation.license2)
                    if (relation.license3 != null)
                        licenses.add(relation.license3)
                    if (relation.license4 != null)
                        licenses.add(relation.license4)
                    val layout = initLicense(relation.id, relation.photo, relation.username, relation.introduction, licenses)
                    relation_infos.addView(layout)
                }
            }
            val applyListStatus = UserRepository.getPostLinkUser(1000, InfoRepository.user!!.id, 1)
            if (applyListStatus == StatusRepository.SUCCESS) {
                val dealingList =
                    UserRepository.postLinkList.filter { it.isPass == 0 } as ArrayList
                val count = dealingList.count()
                if (count > 0) {
                    relation_attention.visibility = View.VISIBLE
                    relation_attention.text = if (count > 99) "99+" else count.toString()
                } else {
                    relation_attention.visibility = View.INVISIBLE
                }
                relation_apply.setOnClickListener {
                    val contentView =
                        LayoutInflater.from(this).inflate(R.layout.popup_friendapply, null)
                    val popWindow = PopupWindow(
                        contentView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        true
                    )
                    popWindow.contentView = contentView
                    val applyCards = contentView.findViewById<LinearLayout>(R.id.friendapply_cards)
                    val applyRefresh =
                        contentView.findViewById<com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout>(
                            R.id.friendapply_refresh
                        )
                    applyRefresh.setAutoLoadMore(true)
                    applyRefresh.setEnableRefresh(false)
                    applyRefresh.setEnableOverScroll(false)
                    applyRefresh.setEnableLoadmore(false)


                    for (applyRelation in UserRepository.postLinkList) {
                        if (applyRelation.isPass == 0) {
                            val view = FriendApplyCardView(
                                this,
                                applyRelation.id,
                                applyRelation.photo,
                                applyRelation.username,
                                applyRelation.introduction?:""
                            )
                            val agreeBtn = view.findViewById<ImageView>(R.id.friendapplyCard_agree)
                            agreeBtn.setOnClickListener {
                                val status = UserRepository.postJudgeLinkUser(
                                    applyRelation.id,
                                    1,
                                    InfoRepository.user!!.id
                                )
                                if (status == StatusRepository.SUCCESS) {
                                    ToastView(this).show("添加亲友成功")
                                    applyCards.removeView(view)
                                    initData()
                                } else
                                    ToastView(this).show("添加亲友失败")
                            }
                            val refuseBtn =
                                view.findViewById<ImageView>(R.id.friendapplyCard_refuse)
                            refuseBtn.setOnClickListener {
                                val status = UserRepository.postJudgeLinkUser(
                                    applyRelation.id,
                                    2,
                                    InfoRepository.user!!.id
                                )
                                if (status == StatusRepository.SUCCESS) {
                                    ToastView(this).show("拒绝成功")
                                    applyCards.removeView(view)
                                    initData()
                                } else
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

                    val rootView =
                        LayoutInflater.from(this).inflate(R.layout.activity_relation, null)
                    popWindow.animationStyle = R.style.contextCommentAnim
                    popWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
                }
            }
        }
    }

    private fun initLicense(id: String, photo: String, username: String, introduction: String, licenses: List<String>): LinearLayout {
        val layout = LinearLayout(this)
        val params = ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = params
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(UserCardView(this, id, photo, username, introduction?:""))
        val childLayout = LinearLayout(this)
        childLayout.layoutParams = params
        childLayout.orientation = LinearLayout.VERTICAL
        for (license in licenses) {
            val textView = TextView(this)
            textView.setTextColor(Color.BLACK)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
            textView.text = license
            textView.gravity = Gravity.CENTER
            childLayout.addView(textView)
        }
        layout.background = resources.getDrawable(R.drawable.bk_relationcard)
        layout.setPadding(20, 30, 20, 30)
        layout.addView(childLayout)
        layout.setOnLongClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("提示:")
                .setMessage("是否确定与用户 $username 解除联结关系?")
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        val deleteStatus = UserRepository.deleteRemoveLink(InfoRepository.user!!.id, id)
                        if (deleteStatus == StatusRepository.SUCCESS) {
                            initData()
                            ToastView(this).show("解除成功")
                        }
                    })
                .setNegativeButton("取消" ,null)
                .create()
                .show()
            true
        }
        return layout
    }
}