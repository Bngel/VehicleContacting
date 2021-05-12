package com.example.vehiclecontacting.TabFragment

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.vehiclecontacting.Adapter.MyBannerAdapter
import com.example.vehiclecontacting.R
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
        bannerEvent(getBannerImage())
    }

    /***
     *  events of banner
     */
    private fun bannerEvent(images: List<Int>) {
        home_banner.addBannerLifecycleObserver(this)
            .setAdapter(MyBannerAdapter(images))
            .setIndicator(CircleIndicator(context))
    }

    /***
     *  get images for banner
     */
    private fun getBannerImage() : List<Int>{
        val images = ArrayList<Int>()
        images.addAll(
            listOf(
                R.drawable.gw_home,
                R.drawable.gw_user,
                R.drawable.gw_community
            )
        )
        return images
    }
}