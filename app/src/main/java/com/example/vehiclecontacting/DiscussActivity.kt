package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.vehiclecontacting.Web.DiscussController.OwnerComment
import kotlinx.android.synthetic.main.activity_discuss.*

class DiscussActivity : AppCompatActivity() {

    private lateinit var ownerComment: OwnerComment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discuss)
        initWidget()
        initData()
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        discuss_close.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        ownerComment = intent.getParcelableExtra("ownerComment")
        discuss_avt.setAvt(ownerComment.userPhoto)
        discuss_title.text = ownerComment.title
        discuss_username.text = ownerComment.username
        discuss_userDescription.text = ""
        discuss_text.text = ownerComment.description
        discuss_browse.text = if (ownerComment.scanCounts > 99) "99+" else ownerComment.scanCounts.toString()
        discuss_favor.text = if (ownerComment.favorCounts > 99) "99+" else ownerComment.favorCounts.toString()
        discuss_comment.text = if (ownerComment.commentCounts > 99) "99+" else ownerComment.commentCounts.toString()
        discuss_like.text = "赞同 ${ownerComment.likeCounts}"
        if (ownerComment.photo1 != "")
            discuss_content.addView(initImg(ownerComment.photo1))
        if (ownerComment.photo2 != "")
            discuss_content.addView(initImg(ownerComment.photo2))
        if (ownerComment.photo3 != "")
            discuss_content.addView(initImg(ownerComment.photo3))
    }

    private fun initImg(photo: String): ImageView {
        val img = ImageView(this)
        Glide.with(this)
            .load(photo)
            .into(img)
        return img
    }
}