package com.example.vehiclecontacting.Activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.Comment
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Web.DiscussController.OwnerComment
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.FirstCommentCardView
import com.example.vehiclecontacting.Widget.SecondCommentCardView
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_discuss.*

class DiscussActivity : BaseActivity() {

    private lateinit var ownerComment: OwnerComment
    private lateinit var comments: ArrayList<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discuss)
        initData()
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        followEvent()
        likeAndFavorEvent()
        deleteEvent()
        commentEvent()
        avtEvent()
    }

    private fun avtEvent() {
        discuss_avt.setOnClickListener {
            val detailIntent = Intent(this, UserDetailActivity::class.java)
            if (InfoRepository.loginStatus.status)
                detailIntent.putExtra("isSelf", ownerComment.fromId == InfoRepository.user!!.id)
            else
                detailIntent.putExtra("isSelf", false)
            detailIntent.putExtra("id", ownerComment.fromId)
            startActivityForResult(detailIntent, ActivityCollector.ACTIVITY_DETAIL)
        }
    }

    private fun deleteEvent() {
        if (InfoRepository.loginStatus.status && InfoRepository.user!!.id == DiscussRepository.ownerComment.fromId) {
            discuss_delete.visibility = View.VISIBLE
            discuss_delete.setOnClickListener {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("提示:")
                    .setMessage("是否确定删除帖子")
                    .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        val delete = DiscussRepository.deleteDiscuss(InfoRepository.user!!.id, DiscussRepository.ownerComment.number)
                        if (delete == StatusRepository.SUCCESS) {
                            ToastView(this).show("删除成功")
                            val intent = Intent()
                            intent.putExtra("update", true)
                            setResult(RESULT_OK, intent)
                            ToastView(this).show("删除成功")
                            finish()
                        }
                        else {
                            ToastView(this).show("删除失败")
                            finish()
                        }
                    })
                    .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialogInterface, i ->  })
                    .show()
            }
        }
    }

    private fun likeAndFavorEvent() {
        var lf = -1
        if (InfoRepository.loginStatus.status) {
            lf = DiscussRepository.postLikeAndFavor(InfoRepository.user!!.id, DiscussRepository.ownerComment.number)
            if (lf == StatusRepository.SUCCESS) {
                if (DiscussRepository.like == DiscussRepository.LIKE_STATUS) {
                    discuss_likeImg.visibility = View.VISIBLE
                    discuss_like.background = resources.getDrawable(R.drawable.bk_likebtn)
                    discuss_likeText.text = "赞同 ${ownerComment.likeCounts}"
                    DiscussRepository.like = DiscussRepository.LIKE_STATUS
                }
                else if (DiscussRepository.like == DiscussRepository.LIKED_STATUS) {
                    discuss_likeImg.visibility = View.GONE
                    discuss_like.background = resources.getDrawable(R.drawable.bk_likedbtn)
                    discuss_likeText.text = "赞同 ${ownerComment.likeCounts}"
                    DiscussRepository.like = DiscussRepository.LIKED_STATUS
                }
                if (DiscussRepository.favor == DiscussRepository.FAVOR_STATUS) {
                    discuss_favorText.text = ownerComment.favorCounts.toString()
                    discuss_favorImg.setImageResource(R.drawable.yp_favor)
                    DiscussRepository.favor = DiscussRepository.FAVOR_STATUS
                }
                else if (DiscussRepository.favor == DiscussRepository.FAVORED_STATUS) {
                    discuss_favorText.text = ownerComment.favorCounts.toString()
                    discuss_favorImg.setImageResource(R.drawable.yy_favor)
                    DiscussRepository.favor = DiscussRepository.FAVORED_STATUS
                }
            }
        }
        likeEvent(lf)
        favorEvent(lf)
    }

    private fun likeEvent(lf: Int) {
        discuss_like.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                if (lf == StatusRepository.SUCCESS) {
                    if (DiscussRepository.like == DiscussRepository.LIKE_STATUS) {
                        val likeStatus = DiscussRepository.postLikeDiscuss(InfoRepository.user!!.id, DiscussRepository.ownerComment.number)
                        if (likeStatus == StatusRepository.SUCCESS) {
                            discuss_likeImg.visibility = View.GONE
                            discuss_like.background = resources.getDrawable(R.drawable.bk_likedbtn)
                            ownerComment.likeCounts += 1
                            discuss_likeText.text = "赞同 ${ownerComment.likeCounts}"
                            DiscussRepository.like = DiscussRepository.LIKED_STATUS
                        }
                        else {
                            ToastView(this).show("点赞帖子失败, 请稍后重试")
                        }
                    }
                    else if (DiscussRepository.like == DiscussRepository.LIKED_STATUS) {
                        val likeStatus = DiscussRepository.deleteLikeDiscuss(InfoRepository.user!!.id, DiscussRepository.ownerComment.number)
                        if (likeStatus == StatusRepository.SUCCESS) {
                            discuss_likeImg.visibility = View.VISIBLE
                            discuss_like.background = resources.getDrawable(R.drawable.bk_likebtn)
                            ownerComment.likeCounts -= 1
                            discuss_likeText.text = "赞同 ${ownerComment.likeCounts}"
                            DiscussRepository.like = DiscussRepository.LIKE_STATUS
                        }
                        else {
                            ToastView(this).show("取消点赞帖子失败, 请稍后重试")
                        }
                    }
                }
                else {
                    ToastView(this).show("读取用户点赞与收藏状态失败")
                }
            }
            else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun favorEvent(lf: Int) {
        discuss_favor.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                if (lf == StatusRepository.SUCCESS) {
                    if (DiscussRepository.favor == DiscussRepository.FAVOR_STATUS) {
                        val favorStatus = DiscussRepository.postFavorDiscuss(InfoRepository.user!!.id, DiscussRepository.ownerComment.number)
                        if (favorStatus == StatusRepository.SUCCESS) {
                            ownerComment.favorCounts += 1
                            discuss_favorText.text = ownerComment.favorCounts.toString()
                            discuss_favorImg.setImageResource(R.drawable.yy_favor)
                            DiscussRepository.favor = DiscussRepository.FAVORED_STATUS
                            ToastView(this).show("收藏成功")
                        }
                        else {
                            ToastView(this).show("收藏帖子失败, 请稍后重试")
                        }
                    }
                    else if (DiscussRepository.favor == DiscussRepository.FAVORED_STATUS) {
                        val favorStatus = DiscussRepository.deleteFavorDiscuss(InfoRepository.user!!.id, DiscussRepository.ownerComment.number)
                        if (favorStatus == StatusRepository.SUCCESS) {
                            ownerComment.favorCounts -= 1
                            discuss_favorText.text = ownerComment.favorCounts.toString()
                            discuss_favorImg.setImageResource(R.drawable.yp_favor)
                            DiscussRepository.favor = DiscussRepository.FAVOR_STATUS
                            ToastView(this).show("取消收藏成功")
                        }
                        else {
                            ToastView(this).show("取消收藏帖子失败, 请稍后重试")
                        }
                    }
                }
                else {
                    ToastView(this).show("读取用户点赞与收藏状态失败")
                }
            }
            else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun followEvent() {
        if (InfoRepository.loginStatus.status && InfoRepository.user!!.id != DiscussRepository.ownerComment.fromId) {
            val followStatus = UserRepository.postJudgeFavor(InfoRepository.user!!.id, DiscussRepository.ownerComment.fromId)
            if (followStatus == StatusRepository.SUCCESS) {
                    discuss_follow.setStatus(UserRepository.followStatus)
                    discuss_follow.discussFollow()
            }
        }
    }

    private fun closeEvent() {
        discuss_close.setOnClickListener {
            val intent = Intent()
            intent.putExtra("update", false)
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }

    private fun commentEvent() {
        discuss_comment.setOnClickListener {
            val contentView = LayoutInflater.from(this).inflate(R.layout.popup_comment, null)
            val popWindow = PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
            popWindow.contentView = contentView
            val commentCards = contentView.findViewById<LinearLayout>(R.id.comment_cards)
            val commentRefresh = contentView.findViewById<com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout>(
                R.id.comment_refresh
            )
            commentRefresh.setAutoLoadMore(true)
            commentRefresh.setEnableRefresh(false)
            commentRefresh.setEnableOverScroll(false)
            for (comment in comments) {
                val view = FirstCommentCardView(this, comment.userPhoto, comment.username,
                    comment.comments, comment.createTime.substring(0,10), comment.likeCounts, comment.commentCounts)
                commentCards.addView(view)

                // 插入二级评论
                val views = SecondCommentCardView(view.context, comment.userPhoto, comment.username,
                    comment.comments, comment.createTime.substring(0,10), comment.likeCounts)
                val secondView = view.findViewById<LinearLayout>(R.id.comment_second_cards)
                secondView.addView(views)
            }
            val commentClose = contentView.findViewById<ImageView>(R.id.comment_close)
            commentClose.setOnClickListener {
                popWindow.dismiss()
            }
            val commentCount = contentView.findViewById<TextView>(R.id.comment_commentCount)
            commentCount.text = "评论 ${comments.count()}"

            val rootView = LayoutInflater.from(this).inflate(R.layout.activity_discuss, null)
            popWindow.animationStyle = R.style.contextCommentAnim
            popWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
        }
    }

    private fun initData() {
        ownerComment = intent.getParcelableExtra("ownerComment")
        comments = intent.getParcelableArrayListExtra("comments")
        discuss_avt.setAvt(ownerComment.userPhoto)
        discuss_title.text = ownerComment.title
        discuss_username.text = ownerComment.username
        discuss_userDescription.text = ""
        discuss_text.text = ownerComment.description
        discuss_browseText.text = if (ownerComment.scanCounts > 99) "99+" else ownerComment.scanCounts.toString()
        discuss_favorText.text = if (ownerComment.favorCounts > 99) "99+" else ownerComment.favorCounts.toString()
        discuss_commentText.text = if (ownerComment.commentCounts > 99) "99+" else ownerComment.commentCounts.toString()
        discuss_likeText.text = "赞同 ${ownerComment.likeCounts}"
        if (ownerComment.photo1 != "")
            discuss_content.addView(initImg(ownerComment.photo1))
        if (ownerComment.photo2 != "")
            discuss_content.addView(initImg(ownerComment.photo2))
        if (ownerComment.photo3 != "")
            discuss_content.addView(initImg(ownerComment.photo3))
    }

    private fun initImg(photo: String): ImageView {
        val img = ImageView(this)
        img.setPadding(10,10,10,10)
        Glide.with(this)
            .load(photo)
            .into(img)
        return img
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("update", false)
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_LOGIN -> {
                    likeAndFavorEvent()
                }
            }
        }
    }
}