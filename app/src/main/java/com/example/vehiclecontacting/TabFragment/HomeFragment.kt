package com.example.vehiclecontacting.TabFragment

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.vehiclecontacting.Adapter.MyBannerAdapter
import com.example.vehiclecontacting.Data.BannerInfo
import com.example.vehiclecontacting.LoginActivity
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Widget.ToastView
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {

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
        bannerEvent(getBannerData())
        testEvent()
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
        val data = ArrayList<BannerInfo>()
        data.addAll(
            listOf(
                BannerInfo(R.drawable.gw_home,"TEXT1TEXT1TEXT1TEXT1TEXT1TEXT1TEXT1"),
                BannerInfo(R.drawable.gw_user,"TEXT2"),
                BannerInfo(R.drawable.gw_community,"TEXT3")
            )
        )
        return data
    }

    private fun testEvent() {
        test_btn.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            //val myToast = ToastView(context!!).show("这是我说的提示框")
        }
    }
}