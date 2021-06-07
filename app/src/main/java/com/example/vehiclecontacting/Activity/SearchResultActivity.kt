package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Web.VehicleController.VehicleRepository
import com.example.vehiclecontacting.Widget.*
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.activity_search_result.*

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
        initDiscuss()
        initUser()
    }

    private fun initDiscuss() {
        DiscussRepository.discussList.clear()
        result_discuss.removeAllViews()
        page = 1
        DiscussRepository.getDiscuss(10, 1, 1, 0, keyword = keyword)
        cardEvent()
    }

    private fun initUser() {
        searchUserByUsername()
        searchUserByLicense()
    }

    private fun searchUserByUsername() {
        val nameStatus = UserRepository.getSearchUser(5, 1, keyword)
        if (nameStatus == StatusRepository.SUCCESS) {
            for (user in UserRepository.searchList) {
                val view = UserCardView(this, user.id, user.photo, user.username, user.introduction?:"")
                val followBtn = view.findViewById<FollowView>(R.id.userCard_follow)
                if (InfoRepository.loginStatus.status) {
                    val followStatus = UserRepository.postJudgeFavor(InfoRepository.user!!.id, user.id)
                    if (followStatus == StatusRepository.SUCCESS)
                        followBtn.setStatus(UserRepository.followStatus)
                    else
                        followBtn.setStatus(UserRepository.FOLLOW_NOT)
                }
                else {
                    followBtn.setStatus(UserRepository.FOLLOW_NOT)
                }
                followBtn.cardFollow(user.id)
                view.setOnClickListener {
                    val detailIntent = Intent(this, UserDetailActivity::class.java)
                    detailIntent.putExtra("isSelf", false)
                    detailIntent.putExtra("id", user.id)
                    startActivityForResult(detailIntent, ActivityCollector.ACTIVITY_DETAIL)
                }
                result_friend.addView(view)
            }
            if (UserRepository.searchCount > 5) {
                result_moreUser.visibility = View.VISIBLE
                result_moreUser.setOnClickListener {
                    result_friend.removeAllViews()
                    val moreStatus = UserRepository.getSearchUser(UserRepository.searchCount, 1, keyword)
                    if (moreStatus == StatusRepository.SUCCESS) {
                        for (user in UserRepository.searchList) {
                            val view = UserCardView(this, user.id, user.photo, user.username, user.introduction?:"")
                            val followBtn = view.findViewById<FollowView>(R.id.userCard_follow)
                            if (InfoRepository.loginStatus.status) {
                                val followStatus = UserRepository.postJudgeFavor(InfoRepository.user!!.id, user.id)
                                if (followStatus == StatusRepository.SUCCESS)
                                    followBtn.setStatus(UserRepository.followStatus)
                                else
                                    followBtn.setStatus(UserRepository.FOLLOW_NOT)
                            }
                            else {
                                followBtn.setStatus(UserRepository.FOLLOW_NOT)
                            }
                            followBtn.cardFollow(user.id)
                            view.setOnClickListener {
                                val detailIntent = Intent(this, UserDetailActivity::class.java)
                                detailIntent.putExtra("isSelf", false)
                                detailIntent.putExtra("id", user.id)
                                startActivityForResult(detailIntent, ActivityCollector.ACTIVITY_DETAIL)
                            }
                            result_friend.addView(view)
                        }
                    }
                    result_moreUser.visibility = View.GONE
                }
            }
        }
    }

    private fun searchUserByLicense() {
        val vehicleStatus = VehicleRepository.getSearchVehicle(100, 1, keyword = keyword)
        if (vehicleStatus == StatusRepository.SUCCESS) {
            for (license in VehicleRepository.searchVehicleList) {
                val view = VehicleCardView(this, license.photo, license.username, license.license, license.vip)
                view.setOnClickListener {
                    val detailIntent = Intent(this, UserDetailActivity::class.java)
                    detailIntent.putExtra("isSelf", false)
                    detailIntent.putExtra("id", license.id)
                    startActivityForResult(detailIntent, ActivityCollector.ACTIVITY_DETAIL)
                }
                result_vehicle.addView(view)
            }
        }
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