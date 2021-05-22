package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.vehiclecontacting.Adapter.MainViewPagerFragmentStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_tab.*
import com.example.vehiclecontacting.AnimRepository.playTabBtnClickAnim
import com.example.vehiclecontacting.StatusRepository.PAGE_COMMUNITY
import com.example.vehiclecontacting.StatusRepository.PAGE_HOME
import com.example.vehiclecontacting.StatusRepository.PAGE_RECOMMEND
import com.example.vehiclecontacting.StatusRepository.PAGE_USER
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.view_communitytitle.*
import kotlinx.android.synthetic.main.view_userinfo.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InfoRepository.DOWNLOAD_PATH = applicationContext.filesDir.absolutePath + "/Vc_Download"
        InfoRepository.PORTRAIT_PATH = applicationContext.filesDir.absolutePath + "/Vc_Avt"
        // 用户状态设置
        initUser()
        // 控件设置
        initWidget()
    }

    private fun initWidget() {
        // viewpager控件属性设置
        vpEvent()
        // Tab控件单击事件绑定
        tabEvent()
    }
    private fun initUser() {
        InfoRepository.initStatus(this)
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
        main_viewpager.adapter = MainViewPagerFragmentStateAdapter(this, 3)
        main_viewpager.isUserInputEnabled = false
        main_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    PAGE_HOME -> {
                        playTabBtnClickAnim(tab_home, R.drawable.yw_home)
                        StatusRepository.homeTabStatus = StatusRepository.HomeTab.HOME
                        tab_community.setImageResource(R.drawable.gw_community)
                        tab_user.setImageResource(R.drawable.gw_user)
                        main_viewpager.currentItem = PAGE_HOME
                    }
                    PAGE_COMMUNITY -> {
                        playTabBtnClickAnim(tab_community, R.drawable.yw_community)
                        StatusRepository.homeTabStatus = StatusRepository.HomeTab.COMMUNITY
                        tab_community.setImageResource(R.drawable.gw_community)
                        tab_home.setImageResource(R.drawable.gw_home)
                        main_viewpager.currentItem = PAGE_COMMUNITY
                    }
                    PAGE_USER -> {
                        playTabBtnClickAnim(tab_user, R.drawable.yw_user)
                        StatusRepository.homeTabStatus = StatusRepository.HomeTab.USER
                        tab_community.setImageResource(R.drawable.gw_community)
                        tab_home.setImageResource(R.drawable.gw_home)
                        main_viewpager.currentItem = PAGE_USER
                    }
                }
            }
        })
    }
}