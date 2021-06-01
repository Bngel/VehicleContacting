package com.example.vehiclecontacting.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.CommunityCardView
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_my_favor.*
import kotlinx.android.synthetic.main.activity_my_history.*

class MyHistoryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_history)
        initData()
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        deleteEvent()
    }

    private fun deleteEvent() {
        history_delete.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("提示:")
                .setMessage("是否清空所有浏览记录")
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        val status = DiscussRepository.deleteAllHistory(InfoRepository.user!!.id)
                        if (status == StatusRepository.SUCCESS) {
                            history_cards.removeAllViews()
                            ToastView(this).show("清空历史记录成功")
                        }
                        else {
                            ToastView(this).show("清空历史记录失败")
                        }
                    })
                .setNegativeButton("取消",null)
                .create()
                .show()
        }
    }

    private fun closeEvent() {
        myHistory_close.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        val context = this
        if (InfoRepository.loginStatus.status) {
            InfoRepository.user!!.apply {
                val discussStatus = DiscussRepository.getHistory(1000, id, 1)
                if (discussStatus == StatusRepository.SUCCESS) {
                    for (discuss in DiscussRepository.historyList) {
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
                        history_cards.addView(view)
                    }
                }
            }
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
                        history_cards.removeAllViews()
                        if (InfoRepository.loginStatus.status) {
                            DiscussRepository.getDiscuss(10, 1, 1, 0)
                            val discussStatus = DiscussRepository.getHistory(1000, InfoRepository.user!!.id, 1)
                            if (discussStatus == StatusRepository.SUCCESS) {
                                for (discuss in DiscussRepository.historyList) {
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
                                    history_cards.addView(view)
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