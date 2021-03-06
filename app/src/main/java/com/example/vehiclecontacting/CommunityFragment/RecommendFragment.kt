package com.example.vehiclecontacting.CommunityFragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Activity.DiscussActivity
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.CommunityCardView
import com.example.vehiclecontacting.Widget.ToastView
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment: Fragment() {

    var parentContext: Context? = null
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        refreshEvent()
        initData()
    }

    private fun initData() {
        DiscussRepository.discussList.clear()
        recommend_cards.removeAllViews()
        page = 1
        DiscussRepository.getDiscuss(10, 1, 1, 0)
        cardEvent()
    }

    private fun refreshEvent() {
        recommend_refresh.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                Handler().postDelayed(Runnable {
                    DiscussRepository.discussList.clear()
                    recommend_cards.removeAllViews()
                    DiscussRepository.getDiscuss(10, 1, 1, 0)
                    page = 1
                    cardEvent()
                    recommend_refresh.finishRefreshing()
                }, 2000)
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                Handler().postDelayed(Runnable {
                    page ++
                    if (page <= DiscussRepository.pageCount){
                        DiscussRepository.getDiscuss(10, 1, page, 0)
                        cardEvent()
                    }
                    else
                        ToastView(parentContext!!).show("??????????????????????????????????????????")
                    recommend_refresh.finishLoadmore()
                }, 2000)
            }
        })
    }

    private fun cardEvent() {
        if (parentContext != null) {
            for (discuss in DiscussRepository.discussList) {
                val view = CommunityCardView(parentContext!!, discuss.title, discuss.userPhoto, discuss.username,discuss.description,discuss.photo,
                    discuss.likeCounts,discuss.commentCounts)
                view.setOnClickListener {
                    if (InfoRepository.loginStatus.status)
                        DiscussRepository.getFirstDiscuss(InfoRepository.user!!.id, 30, discuss.number)
                    else
                        DiscussRepository.getFirstDiscuss(30, discuss.number)
                    val discussIntent = Intent(parentContext, DiscussActivity::class.java)
                    discussIntent.putExtra("ownerComment", DiscussRepository.ownerComment)
                    discussIntent.putExtra("firstComments", DiscussRepository.firstCommentList)
                    startActivityForResult(discussIntent, ActivityCollector.ACTIVITY_DISCUSS)
                }
                recommend_cards.addView(view)
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
                        recommend_cards.removeAllViews()
                        DiscussRepository.getDiscuss(10, 1, 1, 0)
                        page = 1
                        cardEvent()
                        data.putExtra("update", false)
                    }
                }
            }
        }
    }

}