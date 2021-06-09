package com.example.vehiclecontacting.TabFragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vehiclecontacting.*
import com.example.vehiclecontacting.Activity.*
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import kotlinx.android.synthetic.main.view_userfunction.*
import kotlinx.android.synthetic.main.view_userinfo.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserFragment: Fragment() {

    var parentContext: Context? = null
    private var imageFile: File? = null // 声明File对象
    private var imageUri: Uri? = null // 裁剪后的图片uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initData()
        initWidget()
    }

    override fun onResume() {
        super.onResume()
        if (InfoRepository.loginStatus.status) {
            InfoRepository.refreshStatus()
            loadInfo()
        }
    }

    private fun initNotLogin() {
        InfoRepository.quitStatus(parentContext!!)
        if (!InfoRepository.loginStatus.status) {
            user_username.text = resources.getString(R.string.user_login)
            user_vipstatus.text = "未开通VIP服务"
            user_moments.text = ""
            user_follow.text = ""
            user_fans.text = ""
            user_avt.setAvt(resources.getDrawable(R.drawable.bp_defaultavt))
            // user_username.isClickable = false
            user_avt.isClickable = false
            user_setting.visibility = View.GONE
            user_push.visibility = View.GONE
        }
        settingEvent()
        pushEvent()
    }

    private fun initData() {
        if (InfoRepository.loginStatus.status && InfoRepository.user != null) {
            loadInfo()
        }
    }

    private fun initWidget() {
        userNameEvent()
        followEvent()
        fansEvent()
        settingEvent()
        myVehicleEvent()
        myCreateEvent()
        myFavorEvent()
        myHistoryEvent()
        pushEvent()
    }

    private fun pushEvent() {
        if (InfoRepository.loginStatus.status) {
            user_push.visibility = View.VISIBLE
            user_push.setOnClickListener {
                val pushIntent = Intent(parentContext!!, PushActivity::class.java)
                startActivityForResult(pushIntent, ActivityCollector.ACTIVITY_PUSH)
            }
        }
        else {
            user_push.visibility = View.GONE
        }
    }

    private fun myHistoryEvent() {
        user_myHistory.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                val historyIntent = Intent(parentContext!!, MyHistoryActivity::class.java)
                startActivityForResult(historyIntent, ActivityCollector.ACTIVITY_MY_HISTORY)
            } else {
                val loginIntent = Intent(parentContext, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun myFavorEvent() {
        user_myFavor.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                val favorIntent = Intent(parentContext!!, MyFavorActivity::class.java)
                startActivityForResult(favorIntent, ActivityCollector.ACTIVITY_MY_FAVOR)
            } else {
                val loginIntent = Intent(parentContext, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun myCreateEvent() {
        user_myCreate.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                val createIntent = Intent(parentContext!!, MyCreateActivity::class.java)
                startActivityForResult(createIntent, ActivityCollector.ACTIVITY_MY_CREATE)
            } else {
                val loginIntent = Intent(parentContext, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun myVehicleEvent() {
        user_myVehicle.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                val vehicleIntent = Intent(parentContext!!, MyVehicleActivity::class.java)
                startActivityForResult(vehicleIntent, ActivityCollector.ACTIVITY_MY_VEHICLE)
            } else {
                val loginIntent = Intent(parentContext, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun settingEvent() {
        if (InfoRepository.loginStatus.status) {
            user_setting.visibility = View.VISIBLE
            user_setting.setOnClickListener {
                val stIntent = Intent(parentContext!!, SettingActivity::class.java)
                startActivityForResult(stIntent, ActivityCollector.ACTIVITY_SETTING)
            }
        }
        else {
            user_setting.visibility = View.GONE
        }
    }

    private fun fansEvent() {
        user_fans.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                val fansIntent = Intent(parentContext!!, FansActivity::class.java)
                startActivityForResult(fansIntent, ActivityCollector.ACTIVITY_FANS)
            } else {
                val loginIntent = Intent(parentContext, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun followEvent() {
        user_follow.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                val followIntent = Intent(parentContext!!, FollowActivity::class.java)
                startActivityForResult(followIntent, ActivityCollector.ACTIVITY_FOLLOW)
            } else {
                val loginIntent = Intent(parentContext, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun userNameEvent() {
        user_username.setOnClickListener {
            if (InfoRepository.loginStatus.status) {
                val detailIntent = Intent(parentContext, UserDetailActivity::class.java)
                detailIntent.putExtra("isSelf", true)
                detailIntent.putExtra("id", InfoRepository.user!!.id)
                startActivityForResult(detailIntent, ActivityCollector.ACTIVITY_DETAIL)
            }
            else {
                val loginIntent = Intent(parentContext, LoginActivity::class.java)
                startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
            }
        }
    }

    private fun loadInfo() {
        InfoRepository.user!!.apply {
            user_username.text = username?:""
            when {
                vip <= 0 -> {
                    user_vipstatus.text ="未开通VIP服务"
                }
                vip < 100 -> {
                    user_vipstatus.text ="vip${vip}"
                }
                else -> {
                    user_vipstatus.text ="社区管理员"
                }
            }
            user_moments.text = discussCounts.toString()
            user_follow.text = followCounts.toString()
            user_fans.text = fansCounts.toString()
            if (photo != null)
                user_avt.setAvt(photo)
        }
        // user_username.isClickable = false
        user_avt.isClickable = true
        user_avt.setOnClickListener {
            callGallery()
        }
    }

    /**
     * 创建File保存图片
     */
    private fun createImageFile() {
        try {
            if (imageFile != null && imageFile!!.exists()) {
                imageFile!!.delete()
            }
            // 新建文件
            val PORTRAIT = "portrait.png"
            val filepath = "${InfoRepository.PORTRAIT_PATH}/${InfoRepository.user?.username}${PORTRAIT}"
            imageFile = File(
                parentContext!!.getExternalFilesDir(null),
                System.currentTimeMillis().toString() + PORTRAIT
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // 以startActivityForResult的方式启动一个activity用来获取返回的结果
        startActivityForResult(intent, ActivityCollector.CODE_GALLERY)
    }

    /**
     * 显示图片
     * @param imageUri 图片的uri
     */
    private fun displayImage(imageUri: Uri) {
        val file = File(imageUri.path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val result = UserRepository.postUserPhoto(InfoRepository.user!!.id, part)
        if (result == StatusRepository.SUCCESS) {
            InfoRepository.user = UserRepository.getUser(InfoRepository.user!!.phone)
            loadInfo()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_SETTING -> {
                    if (data?.getBooleanExtra("quit", false) == true)
                        initNotLogin()
                }
                ActivityCollector.ACTIVITY_LOGIN -> {
                    val status = data?.getBooleanExtra(StatusRepository.loginStatus, false)
                    if (status == true) {
                        loadInfo()
                        settingEvent()
                        pushEvent()
                    }
                }
                ActivityCollector.CROP_PHOTO -> {
                    try {
                        displayImage(imageUri!!)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                ActivityCollector.CODE_GALLERY -> {
                    val uri: Uri = data!!.data!! // 获取图片的uri
                    val intent_gallery_crop = Intent("com.android.camera.action.CROP")
                    intent_gallery_crop.setDataAndType(uri, "image/*")
                    // 设置裁剪
                    intent_gallery_crop.putExtra("crop", "true")
                    intent_gallery_crop.putExtra("scale", true)
                    // aspectX aspectY 是宽高的比例
                    intent_gallery_crop.putExtra("aspectX", 1)
                    intent_gallery_crop.putExtra("aspectY", 1)
                    // outputX outputY 是裁剪图片宽高
                    intent_gallery_crop.putExtra("outputX", 400)
                    intent_gallery_crop.putExtra("outputY", 400)
                    intent_gallery_crop.putExtra("return-data", false)
                    // 创建文件保存裁剪的图片
                    createImageFile()
                    imageUri = Uri.fromFile(imageFile)
                    if (imageUri != null) {
                        intent_gallery_crop.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                        intent_gallery_crop.putExtra(
                            "outputFormat",
                            Bitmap.CompressFormat.JPEG.toString()
                        )
                    }
                    startActivityForResult(intent_gallery_crop, ActivityCollector.CROP_PHOTO)
                }
            }
        }
    }
}