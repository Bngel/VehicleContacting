package com.example.vehiclecontacting.TabFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vehiclecontacting.Adapter.MyBannerAdapter
import com.example.vehiclecontacting.Data.BannerInfo
import com.example.vehiclecontacting.Data.HotInfo
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Widget.HomeHotView
import com.example.vehiclecontacting.Widget.ToastView
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*

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
        bannerEvent(getBannerData())
        hotEvent()
    }

    /***
     *  events of banner
     */
    private fun bannerEvent(data: List<BannerInfo>) {
        home_banner.addBannerLifecycleObserver(this)
            .setAdapter(MyBannerAdapter(data))
            .setIndicator(CircleIndicator(context))
    }

    /***
     *  get images for banner
     */
    private fun getBannerData() : List<BannerInfo>{
        return listOf(
                BannerInfo(ContextCompat.getDrawable(parentContext!!, R.drawable.gw_home)!!,"TEXT1TEXT1TEXT1TEXT1TEXT1TEXT1TEXT1"),
                BannerInfo(ContextCompat.getDrawable(parentContext!!, R.drawable.gw_user)!!,"TEXT2"),
                BannerInfo(ContextCompat.getDrawable(parentContext!!, R.drawable.gw_community)!!,"TEXT3")
        )
    }

    private fun hotEvent() {
        val hots = getHots()
        if (parentContext != null) {
            for (hot in hots) {
                val view = HomeHotView(parentContext!!, hot.title, hot.type, hot.img)
                view.setOnClickListener {
                    ToastView(parentContext!!).show(hot.title)
                }
                home_hot.addView(view)
            }
        }
    }

    private fun getHots(): List<HotInfo> {
        return listOf(
            HotInfo("腾讯、网易云音乐分别与索尼音乐达成合作", "3分钟前·车友杂谈", ContextCompat.getDrawable(parentContext!!, R.drawable.bk_checkrect)!!),
            HotInfo("生存游戏《往日不再》Steam发售,售价279元", "25分钟前·PC游戏", ContextCompat.getDrawable(parentContext!!, R.drawable.bk_checkrect)!!),
            HotInfo("测试一下3", "测试一下", ContextCompat.getDrawable(parentContext!!, R.drawable.bk_checkrect)!!),
            HotInfo("测试一下4", "测试一下", ContextCompat.getDrawable(parentContext!!, R.drawable.bk_checkrect)!!)
        )
    }
}