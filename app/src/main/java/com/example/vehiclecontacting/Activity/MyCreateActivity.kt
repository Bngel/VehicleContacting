package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.os.Bundle
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.CommunityCardView
import kotlinx.android.synthetic.main.activity_my_create.*
import kotlinx.android.synthetic.main.fragment_recommend.*

class MyCreateActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_create)
        initData()
        initWidget()
    }

    private fun initData() {
        val context = this
        if (InfoRepository.loginStatus.status) {
            InfoRepository.user!!.apply {
                myCreate_avt.setAvt(photo)
                myCreate_username.text = username
                myCreate_discussCount.text = "文章数: $discussCounts"
                myCreate_fansCount.text = "粉丝数: $fansCounts"
                val discussStatus = DiscussRepository.getUserDiscuss(discussCounts, id, 1)
                if (discussStatus == StatusRepository.SUCCESS) {
                    for (discuss in DiscussRepository.myDiscussList) {
                        val view = CommunityCardView(context, discuss.title, discuss.userPhoto,
                            discuss.username, discuss.description, discuss.photo, discuss.likeCounts, discuss.commentCounts)
                        view.setOnClickListener {
                            DiscussRepository.getFirstDiscuss(30, discuss.number)
                            val discussIntent = Intent(context, DiscussActivity::class.java)
                            discussIntent.putExtra("ownerComment", DiscussRepository.ownerComment)
                            discussIntent.putExtra("firstComments", DiscussRepository.firstCommentList)
                            startActivityForResult(discussIntent, ActivityCollector.ACTIVITY_DISCUSS)
                        }
                        myCreate_cards.addView(view)
                    }
                }
            }
        }
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        myCreate_close.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_DISCUSS -> {
                    val delete = data?.getBooleanExtra("update", false)
                    if (delete == true) {
                        DiscussRepository.discussList.clear()
                        recommend_cards.removeAllViews()
                        if (InfoRepository.loginStatus.status) {
                            DiscussRepository.getDiscuss(10, 1, 1, 0)
                            val discussStatus = DiscussRepository.getUserDiscuss(InfoRepository.user!!.discussCounts, InfoRepository.user!!.id, 1)
                            if (discussStatus == StatusRepository.SUCCESS) {
                                for (discuss in DiscussRepository.myDiscussList) {
                                    val view = CommunityCardView(this, discuss.title, discuss.userPhoto,
                                        discuss.username, discuss.description, discuss.photo, discuss.likeCounts, discuss.commentCounts)
                                    view.setOnClickListener {
                                        DiscussRepository.getFirstDiscuss(30, discuss.number)
                                        val discussIntent = Intent(this, DiscussActivity::class.java)
                                        discussIntent.putExtra("ownerComment", DiscussRepository.ownerComment)
                                        discussIntent.putExtra("firstComments", DiscussRepository.firstCommentList)
                                        startActivityForResult(discussIntent, ActivityCollector.ACTIVITY_DISCUSS)
                                    }
                                    myCreate_cards.addView(view)
                                }
                            }
                        }
                        data.putExtra("update", false)
                    }
                }
            }
        }
    }
}