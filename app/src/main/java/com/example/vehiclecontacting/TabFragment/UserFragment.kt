package com.example.vehiclecontacting.TabFragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.IDNA
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.vehiclecontacting.*
import com.example.vehiclecontacting.Web.UserController.UserRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_user.*
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
        initWidget()
    }

    override fun onResume() {
        super.onResume()
        if (InfoRepository.loginStatus.status) {
            InfoRepository.refreshStatus()
            loadInfo()
        }
    }

    private fun initWidget() {
        val status = InfoRepository.loginStatus.status
        val user = InfoRepository.user
        if (status) {
            if (user != null) {
                loadInfo()
            }
        }
        else {
            loginEvent()
        }
    }

    private fun loginEvent() {
        user_username.setOnClickListener {
            val loginIntent = Intent(parentContext, LoginActivity::class.java)
            startActivityForResult(loginIntent, ActivityCollector.ACTIVITY_LOGIN)
        }
    }

    private fun loadInfo() {
        user_username.text = InfoRepository.user!!.username?:""
        user_vipstatus.text =
            if (InfoRepository.user!!.vip > 0) "vip${InfoRepository.user!!.vip}" else "未开通VIP服务"
        user_moments.text = InfoRepository.user!!.momentCounts.toString()
        user_follow.text = InfoRepository.user!!.followCounts.toString()
        user_fans.text = InfoRepository.user!!.fansCounts.toString()
        if (InfoRepository.user!!.photo != null)
            user_avt.setAvt(InfoRepository.user!!.photo)
        user_username.isClickable = false
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
                ActivityCollector.ACTIVITY_LOGIN -> {
                    val status = data?.getBooleanExtra(StatusRepository.loginStatus, false)
                    if (status == true) {
                        loadInfo()
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