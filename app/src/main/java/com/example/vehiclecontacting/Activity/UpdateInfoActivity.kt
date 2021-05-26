package com.example.vehiclecontacting.Activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_update_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UpdateInfoActivity : BaseActivity() {

    private var imageFile: File? = null // 声明File对象
    private var imageUri: Uri? = null // 裁剪后的图片uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_info)
        initData()
        initWidget()
    }

    private fun initData() {
        InfoRepository.user!!.apply {
            updateInfo_avt.setAvt(photo)
            updateInfo_description.text = introduction?:""
            updateInfo_username.text = username
            updateInfo_sex.text = sex
        }
    }

    private fun initWidget(){
        avtEvent()
        closeEvent()
        usernameEvent()
        descriptionEvent()
        sexEvent()
    }

    private fun sexEvent() {
        updateInfo_sex.setOnClickListener {
            changeSex()
        }
        updateInfo_changeSex.setOnClickListener {
            changeSex()
        }
    }

    private fun changeSex() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("提示:")
            .setMessage("是否确定切换性别")
            .setPositiveButton("确定",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val sex = InfoRepository.user!!.sex
                    if (sex == "男"){
                        val status = UserRepository.patchUserSex(InfoRepository.user!!.id, "女")
                        if (status == StatusRepository.SUCCESS) {
                            ToastView(this).show("切换成功")
                            updateInfo_sex.text = "女"
                            InfoRepository.user = UserRepository.getUser(InfoRepository.user!!.phone)
                            initData()
                        }
                        else
                            ToastView(this).show("切换失败")
                    }
                    else {
                        val status = UserRepository.patchUserSex(InfoRepository.user!!.id, "男")
                        if (status == StatusRepository.SUCCESS) {
                            ToastView(this).show("切换成功")
                            updateInfo_sex.text = "男"
                            InfoRepository.user = UserRepository.getUser(InfoRepository.user!!.phone)
                            initData()
                        }
                        else
                            ToastView(this).show("切换失败")
                    }
                })
            .setNegativeButton("取消",
                DialogInterface.OnClickListener { dialogInterface, i ->  })
            .show()
    }

    private fun avtEvent() {
        updateInfo_avt.hideVip()
        updateInfo_avt.setOnClickListener {
            callGallery()
        }
    }

    private fun closeEvent() {
        updateInfo_close.setOnClickListener {
            finish()
        }
    }

    private fun usernameEvent() {
        updateInfo_username.setOnClickListener {
            val unIntent = Intent(this, ChangeInfoActivity::class.java)
            unIntent.putExtra("type", "username")
            startActivityForResult(unIntent, ActivityCollector.ACTIVITY_CHANGE_INFO)
        }
        updateInfo_changeUsername.setOnClickListener {
            val unIntent = Intent(this, ChangeInfoActivity::class.java)
            unIntent.putExtra("type", "username")
            startActivityForResult(unIntent, ActivityCollector.ACTIVITY_CHANGE_INFO)
        }
    }

    private fun descriptionEvent() {
        updateInfo_description.setOnClickListener {
            val dpIntent = Intent(this, ChangeInfoActivity::class.java)
            dpIntent.putExtra("type", "description")
            startActivityForResult(dpIntent, ActivityCollector.ACTIVITY_CHANGE_INFO)
        }
        updateInfo_changeDescription.setOnClickListener {
            val dpIntent = Intent(this, ChangeInfoActivity::class.java)
            dpIntent.putExtra("type", "description")
            startActivityForResult(dpIntent, ActivityCollector.ACTIVITY_CHANGE_INFO)
        }
    }

    private fun callGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // 以startActivityForResult的方式启动一个activity用来获取返回的结果
        startActivityForResult(intent, ActivityCollector.CODE_GALLERY)
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
                getExternalFilesDir(null),
                System.currentTimeMillis().toString() + PORTRAIT
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            initData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActivityCollector.ACTIVITY_CHANGE_INFO -> {
                    InfoRepository.user = UserRepository.getUser(InfoRepository.user!!.phone)
                    initData()
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