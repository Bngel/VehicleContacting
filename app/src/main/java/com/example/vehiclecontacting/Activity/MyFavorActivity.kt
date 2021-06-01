package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.os.Bundle
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.CommunityCardView
import kotlinx.android.synthetic.main.activity_my_favor.*

class MyFavorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_favor)
        initData()
        initWidget()
    }

    private fun initData() {
        val context = this
        if (InfoRepository.loginStatus.status) {
            InfoRepository.user!!.apply {
                val discussStatus = DiscussRepository.getFavorDiscuss(1000, id, 1)
                if (discussStatus == StatusRepository.SUCCESS) {
                    for (discuss in DiscussRepository.favorDiscussList) {
                        val view = CommunityCardView(context, discuss.title, discuss.userPhoto,
                            discuss.username, discuss.description, discuss.photo, discuss.likeCounts, discuss.commentCounts)
                        view.setOnClickListener {
                            if (InfoRepository.loginStatus.status)
                                DiscussRepository.getFirstDiscuss(InfoRepository.user!!.id, 30, discuss.number)
                            else
                                DiscussRepository.getFirstDiscuss(30, discuss.number)
                            val discussIntent = Intent(context, DiscussActivity::class.java)
                            discussIntent.putExtra("ownerComment", DiscussRepository.ownerComment)
                            discussIntent.putExtra("firstComments", DiscussRepository.firstCommentList)
                            startActivityForResult(discussIntent, ActivityCollector.ACTIVITY_DISCUSS)
                        }
                        myFavor_cards.addView(view)
                    }
                }
            }
        }
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        myFavor_close.setOnClickListener {
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
                        myFavor_cards.removeAllViews()
                        if (InfoRepository.loginStatus.status) {
                            DiscussRepository.getDiscuss(10, 1, 1, 0)
                            val discussStatus = DiscussRepository.getFavorDiscuss(1000, InfoRepository.user!!.id, 1)
                            if (discussStatus == StatusRepository.SUCCESS) {
                                for (discuss in DiscussRepository.favorDiscussList) {
                                    val view = CommunityCardView(this, discuss.title, discuss.userPhoto,
                                        discuss.username, discuss.description, discuss.photo, discuss.likeCounts, discuss.commentCounts)
                                    view.setOnClickListener {
                                        if (InfoRepository.loginStatus.status)
                                            DiscussRepository.getFirstDiscuss(InfoRepository.user!!.id, 30, discuss.number)
                                        else
                                            DiscussRepository.getFirstDiscuss(30, discuss.number)
                                        val discussIntent = Intent(this, DiscussActivity::class.java)
                                        discussIntent.putExtra("ownerComment", DiscussRepository.ownerComment)
                                        discussIntent.putExtra("firstComments", DiscussRepository.firstCommentList)
                                        startActivityForResult(discussIntent, ActivityCollector.ACTIVITY_DISCUSS)
                                    }
                                    myFavor_cards.addView(view)
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