package com.example.vehiclecontacting.TabFragment

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vehiclecontacting.Activity.*
import com.example.vehiclecontacting.Adapter.MyBannerAdapter
import com.example.vehiclecontacting.Data.BannerInfo
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.HomeHotView
import com.example.vehiclecontacting.Widget.ToastView
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_hometitle.*
import kotlinx.android.synthetic.main.view_userinfo.*


class HomeFragment: Fragment() {

    // 申请相机权限的requestCode
    private val PERMISSION_CAMERA_REQUEST_CODE = 0x00000012

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
        scanEvent()
        friendsEvent()
    }

    private fun friendsEvent() {
        home_friends.setOnClickListener {
                if (InfoRepository.loginStatus.status) {
                    val friendsIntent = Intent(parentContext!!, FriendsActivity::class.java)
                    startActivityForResult(friendsIntent, ActivityCollector.ACTIVITY_FRIENDS)
                } else {
                    val loginIntent = Intent(parentContext, LoginActivity::class.java)
                    startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun scanEvent() {
        home_scan.setOnClickListener {
            checkPermissionAndCamera()
        }
    }

    private fun cityEvent() {
        home_city.setOnClickListener {

        }
    }

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
                    val view = HomeHotView(
                        parentContext!!, hot.title, hot.updateTime.substring(
                            0,
                            10
                        ), hot.photo
                    )
                    view.setOnClickListener {
                        if (InfoRepository.loginStatus.status)
                            DiscussRepository.getFirstDiscuss(
                                InfoRepository.user!!.id,
                                30,
                                hot.number
                            )
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

    /**
     * 检查权限并拍照。
     * 调用相机前先检查权限。
     */
    private fun checkPermissionAndCamera() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(
            activity as Activity,
            Manifest.permission.CAMERA
        )
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            val scanIntent = Intent(parentContext!!, QRScanActivity::class.java)
            startActivity(scanIntent)
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(
                activity as Activity, arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA_REQUEST_CODE
            )
        }
    }

    /**
     * 处理权限申请的回调。
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //允许权限，有调起相机拍照。
                val scanIntent = Intent(parentContext!!, QRScanActivity::class.java)
                startActivity(scanIntent)
            } else {
                //拒绝权限，弹出提示框。
                ToastView(parentContext!!).show( "拍照权限被拒绝")
            }
        }
    }

}