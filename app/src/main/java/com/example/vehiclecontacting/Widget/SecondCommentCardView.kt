package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.CommentOwner
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import kotlinx.android.synthetic.main.view_comment_second.view.*

class SecondCommentCardView: LinearLayout{
    var secondNumber = ""

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(
        context: Context,
        avt: String,
        username: String,
        text: String,
        date: String,
        likeCount: String
    ) : super(context) {
        comment_second_avt.setAvt(avt)
        comment_second_username.text = username
        comment_second_text.text = text
        comment_second_date.text = date
        comment_second_likeCount.text = likeCount
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_comment_second, this)
    }

    fun setData(avt: String, username: String, text: String, date: String, likeCount: String) {
        comment_second_avt.setAvt(avt)
        comment_second_username.text = username
        comment_second_text.text = text
        comment_second_date.text = date
        comment_second_likeCount.text = likeCount
    }

    fun setData(data: CommentOwner) {
        comment_second_avt.setAvt(data.photo)
        comment_second_username.text = data.username
        comment_second_text.text = data.comments
        comment_second_likeCount.text = data.likeCounts.toString()
        comment_second_date.text = data.createTime.substring(0, 10)
        secondNumber = data.number
    }

    fun openSecondComments() {
        val contentView = LayoutInflater.from(context).inflate(R.layout.popup_second_comment, null)
        val popWindow = PopupWindow(
            contentView,
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true
        )
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
        commentFrom.findViewById<ImageView>(R.id.comment_second_commentImg).setOnClickListener {
            DiscussRepository.replyNumber = secondNumber
            val secondEdit = contentView.findViewById<EditText>(R.id.comment_third_edit)
            secondEdit.requestFocus()
            val inputManager = secondEdit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(secondEdit, 0)
        }
        val thirdCommentCards = contentView.findViewById<LinearLayout>(R.id.comment_third_cards)
        for (thirdComment in DiscussRepository.thirdCommentList) {
            val view1 = SecondCommentCardView(
                context,
                thirdComment.photo,
                thirdComment.username,
                thirdComment.description,
                thirdComment.createTime.substring(0, 10),
                thirdComment.likeCounts.toString()
            )
            val commentImg = view1.findViewById<ImageView>(R.id.comment_second_commentImg)
            commentImg.setOnClickListener {
                DiscussRepository.replyNumber = secondNumber
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
