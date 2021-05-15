package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.vehiclecontacting.Adapter.MyBannerAdapter
import com.example.vehiclecontacting.Adapter.ViewPagerFragmentStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_tab.*
import com.example.vehiclecontacting.StatusRepository.Tab
import com.example.vehiclecontacting.StatusRepository.tabStatus
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
            if (tabStatus != Tab.HOME) {
                playTabBtnClickAnim(it as ImageView, R.drawable.yw_home)
                tabStatus = Tab.HOME
                tab_community.setImageResource(R.drawable.gw_community)
                tab_user.setImageResource(R.drawable.gw_user)
                main_viewpager.currentItem = PAGE_HOME
            }
        }
        tab_community.setOnClickListener {
            if (tabStatus != Tab.COMMUNITY) {
                playTabBtnClickAnim(it as ImageView, R.drawable.yw_community)
                tabStatus = Tab.COMMUNITY
                tab_home.setImageResource(R.drawable.gw_home)
                tab_user.setImageResource(R.drawable.gw_user)
                main_viewpager.currentItem = PAGE_COMMUNITY
            }
        }
        tab_user.setOnClickListener {
            if (tabStatus != Tab.USER) {
                playTabBtnClickAnim(it as ImageView, R.drawable.yw_user)
                tabStatus = Tab.USER
                tab_home.setImageResource(R.drawable.gw_home)
                tab_community.setImageResource(R.drawable.gw_community)
                main_viewpager.currentItem = PAGE_USER
            }
        }
        // 默认启动选择首页
        tab_home.setImageResource(R.drawable.yw_home)
        tabStatus = Tab.HOME
    }
    private fun vpEvent() {
        main_viewpager.adapter = ViewPagerFragmentStateAdapter(this, 3)
        main_viewpager.isUserInputEnabled = false
    }
}