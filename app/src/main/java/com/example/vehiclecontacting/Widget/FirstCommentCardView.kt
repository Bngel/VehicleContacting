package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import kotlinx.android.synthetic.main.view_comment_first.view.*

class FirstCommentCardView: LinearLayout {
    private var firstNumber = ""

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, avt: String, username: String, text: String, date: String, likeCount: Int) : super(context) {
        comment_first_avt.setAvt(avt)
        comment_first_username.text = username
        comment_first_text.text = text
        comment_first_date.text = date
        comment_first_likeCount.text = likeCount.toString()
    }
    constructor(context: Context, avt: String, username: String, text: String, date: String, likeCount: Int, commentCount: Int, number: String) : super(context) {
        firstNumber = number
        comment_first_avt.setAvt(avt)
        comment_first_username.text = username
        comment_first_text.text = text
        comment_first_date.text = date
        comment_first_likeCount.text = likeCount.toString()
        if (commentCount > 2) {
            comment_second_count.visibility = View.VISIBLE
            comment_second_count.text = "查看全部 $commentCount 条回复 >"
            comment_second_count.setOnClickListener {
                val thirdStatus = DiscussRepository.getThirdDiscuss(1000, firstNumber, 1)
                if (thirdStatus == StatusRepository.SUCCESS) {
                    openSecondComments()
                }
            }
        }
        else {
            comment_second_count.visibility = View.GONE
        }
        comment_second_count.background = resources.getDrawable(R.drawable.bk_comment_second_count)
        comment_first_commentImg.setOnClickListener {
            val thirdStatus = DiscussRepository.getThirdDiscuss(1000, firstNumber, 1)
            if (thirdStatus == StatusRepository.SUCCESS) {
                openSecondComments()
            }
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_comment_first, this)
    }

    private fun openSecondComments() {
        val contentView = LayoutInflater.from(context).inflate(R.layout.popup_second_comment, null)
        val popWindow = PopupWindow(contentView,
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
        popWindow.contentView = contentView
        val commentClose = contentView.findViewById<ImageView>(R.id.comment_third_close)
        commentClose.setOnClickListener {
            popWindow.dismiss()
        }
        contentView.findViewById<com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout>(R.id.comment_third_refresh).apply {
            setAutoLoadMore(true)
            setEnableRefresh(false)
            setEnableOverScroll(false)
            setEnableLoadmore(false)
        }
        val commentFrom =
            contentView.findViewById<SecondCommentCardView>(R.id.comment_third_from)
        commentFrom.setData(DiscussRepository.thirdOwnerComment)
        commentFrom.setOnClickListener {
            DiscussRepository.replyNumber = firstNumber
            val secondEdit = contentView.findViewById<EditText>(R.id.comment_third_edit)
            secondEdit.requestFocus()
            val inputManager = secondEdit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(secondEdit, 0)
        }
        val thirdCommentCards = contentView.findViewById<LinearLayout>(R.id.comment_third_cards)
        for (thirdComment in DiscussRepository.thirdCommentList) {
            val view1 = SecondCommentCardView(context, thirdComment.photo, thirdComment.username,
                thirdComment.description, thirdComment.createTime.substring(0, 10), thirdComment.likeCounts.toString())
            val commentImg = view1.findViewById<ImageView>(R.id.comment_second_commentImg)
            commentImg.setOnClickListener {
                DiscussRepository.replyNumber = thirdComment.number
                val secondEdit = contentView.findViewById<EditText>(R.id.comment_third_edit)
                secondEdit.requestFocus()
                val inputManager = secondEdit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.showSoftInput(secondEdit, 0)
            }
            thirdCommentCards.addView(view1)
        }

        val rootView = LayoutInflater.from(context).inflate(R.layout.activity_discuss, null)
        popWindow.animationStyle = R.style.contextCommentAnim
        popWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
    }
}