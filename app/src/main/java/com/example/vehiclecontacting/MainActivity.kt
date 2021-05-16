package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.vehiclecontacting.Adapter.MyBannerAdapter
import com.example.vehiclecontacting.Adapter.ViewPagerFragmentStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_tab.*
import com.example.vehiclecontacting.AnimRepository.playTabBtnClickAnim
import com.example.vehiclecontacting.StatusRepository.PAGE_COMMUNITY
import com.example.vehiclecontacting.StatusRepository.PAGE_HOME
import com.example.vehiclecontacting.StatusRepository.PAGE_USER
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 控件设置
        initWidget()
    }

    private fun initWidget() {
        // viewpager控件属性设置
        vpEvent()
        // Tab控件单击事件绑定
        tabEvent()
    }

    private fun tabEvent() {
        tab_home.setOnClickListener {
            if (StatusRepository.homeTabStatus != StatusRepository.HomeTab.HOME) {
                playTabBtnClickAnim(it as ImageView, R.drawable.yw_home)
                StatusRepository.homeTabStatus = StatusRepository.HomeTab.HOME
                tab_community.setImageResource(R.drawable.gw_community)
                tab_user.setImageResource(R.drawable.gw_user)
                main_viewpager.currentItem = PAGE_HOME
            }
        }
        tab_community.setOnClickListener {
            if (StatusRepository.homeTabStatus != StatusRepository.HomeTab.COMMUNITY) {
                playTabBtnClickAnim(it as ImageView, R.drawable.yw_community)
                StatusRepository.homeTabStatus = StatusRepository.HomeTab.COMMUNITY
                tab_home.setImageResource(R.drawable.gw_home)
                tab_user.setImageResource(R.drawable.gw_user)
                main_viewpager.currentItem = PAGE_COMMUNITY
            }
        }
        tab_user.setOnClickListener {
            if (StatusRepository.homeTabStatus != StatusRepository.HomeTab.USER) {
                playTabBtnClickAnim(it as ImageView, R.drawable.yw_user)
                StatusRepository.homeTabStatus = StatusRepository.HomeTab.USER
                tab_home.setImageResource(R.drawable.gw_home)
                tab_community.setImageResource(R.drawable.gw_community)
                main_viewpager.currentItem = PAGE_USER
            }
        }
        // 默认启动选择首页
        tab_home.setImageResource(R.drawable.yw_home)
        StatusRepository.homeTabStatus = StatusRepository.HomeTab.HOME
    }
    private fun vpEvent() {
        main_viewpager.adapter = ViewPagerFragmentStateAdapter(this, 3)
        main_viewpager.isUserInputEnabled = false
    }
}