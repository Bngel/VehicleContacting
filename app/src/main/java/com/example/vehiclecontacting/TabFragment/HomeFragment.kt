package com.example.vehiclecontacting.TabFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vehiclecontacting.Activity.DiscussActivity
import com.example.vehiclecontacting.Adapter.MyBannerAdapter
import com.example.vehiclecontacting.Data.BannerInfo
import com.example.vehiclecontacting.Data.HotInfo
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.HomeHotView
import com.example.vehiclecontacting.Widget.ToastView
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_recommend.*
import kotlinx.android.synthetic.main.view_hometitle.*

class HomeFragment: Fragment() {

    var parentContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /***
     *  load events until all views have loaded
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 在 Activity View 加载完毕后 载入
        // Banner 事件
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        bannerEvent()
        hotEvent()
        cityEvent()
    }

    private fun cityEvent() {
        home_city.setOnClickListener {

        }
    }

    /***
     *  events of banner
     */
    private fun bannerEvent() {
        val status = DiscussRepository.getHotDiscuss()
        if (status == StatusRepository.SUCCESS){
            val bannerInfoList = ArrayList<BannerInfo>()
            for (discuss in DiscussRepository.hotDiscussList) {
                bannerInfoList.add(BannerInfo(discuss.photo, discuss.title, discuss.number))
            }
            home_banner.addBannerLifecycleObserver(this)
                .setAdapter(MyBannerAdapter(bannerInfoList))
                .setIndicator(CircleIndicator(context))
        }
    }

    private fun hotEvent() {
        val hotStatus = DiscussRepository.getFirstPageDiscuss(4)
        if (hotStatus == StatusRepository.SUCCESS) {
            if (parentContext != null) {
                for (hot in DiscussRepository.hotList) {
                    val view = HomeHotView(parentContext!!, hot.title, hot.updateTime.substring(0,10), hot.photo)
                    view.setOnClickListener {
                        if (InfoRepository.loginStatus.status)
                            DiscussRepository.getFirstDiscuss(InfoRepository.user!!.id, 30, hot.number)
                        else
                            DiscussRepository.getFirstDiscuss(30, hot.number)
                        val discussIntent = Intent(parentContext, DiscussActivity::class.java)
                        discussIntent.putExtra("ownerComment", DiscussRepository.ownerComment)
                        discussIntent.putExtra("firstComments", DiscussRepository.firstCommentList)
                        startActivityForResult(discussIntent, ActivityCollector.ACTIVITY_DISCUSS)
                    }
                    home_hot.addView(view)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_DISCUSS -> {

                }
            }
        }
    }
}