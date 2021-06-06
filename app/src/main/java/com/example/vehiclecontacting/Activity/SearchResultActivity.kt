package com.example.vehiclecontacting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.CommunityCardView
import com.example.vehiclecontacting.Widget.ToastView
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.fragment_recommend.*

class SearchResultActivity : BaseActivity() {

    var page = 1
    var keyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        initData()
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        refreshEvent()
    }

    private fun closeEvent() {
        result_close.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        keyword = intent.getStringExtra("keyword")
        DiscussRepository.discussList.clear()
        result_discuss.removeAllViews()
        page = 1
        DiscussRepository.getDiscuss(10, 1, 1, 0, keyword = keyword)
        cardEvent()
    }

    private fun refreshEvent() {
        result_friend_refresh.setEnableRefresh(false)
        result_friend_refresh.setEnableOverScroll(false)
        result_friend_refresh.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                Handler().postDelayed(Runnable {
                    page ++
                    if (page <= DiscussRepository.pageCount){
                        DiscussRepository.getDiscuss(10, 1, page, 0, keyword = keyword)
                        cardEvent()
                    }
                    result_friend_refresh.finishLoadmore()
                }, 2000)
            }
        })
    }

    private fun cardEvent() {
            for (discuss in DiscussRepository.discussList) {
                val view = CommunityCardView(this, discuss.title, discuss.userPhoto, discuss.username,discuss.description,discuss.photo,
                    discuss.likeCounts,discuss.commentCounts)
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
                result_discuss.addView(view)
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
                        result_discuss.removeAllViews()
                        DiscussRepository.getDiscuss(10, 1, 1, 0, keyword = keyword)
                        page = 1
                        cardEvent()
                        data.putExtra("update", false)
                    }
                }
            }
        }
    }
}