package com.example.vehiclecontacting.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import com.example.vehiclecontacting.*
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.AnimRepository
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.view_createtitle.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import kotlin.collections.ArrayList

class CreateActivity : BaseActivity() {

    private var imageFile: File? = null // 声明File对象
    private var imageUri: Uri? = null // 裁剪后的图片uri
    private val imageList = ArrayList<Uri>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initWidget()
    }

    private fun initWidget() {
        sendEvent()
        photoEvent()
        closeEvent()
    }

    private fun closeEvent() {
        create_close.setOnClickListener {
            finish()
        }
    }

    private fun sendEvent() {
        create_send.setOnClickListener {
            if (create_edit.text.toString() == "" || create_title.text.toString() == "")
                ToastView(this).show("标题或内容不能为空")
            else {
                var flag = true
                for (photo in imageList) {
                    flag = saveImage(photo)
                    if (!flag)
                        break
                }
                if (flag) {
                    DiscussRepository.postDiscuss(create_edit.text.toString(), InfoRepository.user!!.id, create_title.text.toString(),
                        DiscussRepository.imageUrl)
                    DiscussRepository.imageUrl.clear()
                    val intent = Intent()
                    intent.putExtra("update", true)
                    setResult(RESULT_OK, intent)
                    ToastView(this).show("发表成功")
                }
                finish()
            }
        }
    }

    /**
     * 显示图片
     * @param imageUri 图片的uri
     */
    private fun saveImage(imageUri: Uri): Boolean {
        val file = File(imageUri.path)
        var flag = true
        if (!file.exists()) {
            flag = false
            ToastView(this).show("发表帖子失败, 请重试")
            return flag
        }
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val status = DiscussRepository.postDiscussPhoto(part)
        if (status != StatusRepository.SUCCESS) {
            ToastView(this).show("发表帖子失败, 请重试")
            flag = false
        }
        return flag
    }

    private fun photoEvent() {
        create_photo.setOnClickListener {
            AnimRepository.playAddArticleClickAnim(it as ImageView)
            if (imageList.count() < 3)
                callGallery()
            else
                ToastView(this).show(resources.getString(R.string.create_limitPhoto))
        }
        create_photo.setOnLongClickListener {
            imageList.clear()
            create_attention.text = imageList.count().toString()
            create_attention.visibility = View.INVISIBLE
            ToastView(this).show(resources.getString(R.string.create_clearPhoto))
            true
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
            // 新建文件
            imageFile = File(
                getExternalFilesDir(null),
                System.currentTimeMillis().toString() + (1..10).random() + ".png"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ActivityCollector.CROP_PHOTO -> {
                    try {
                        imageList.add(imageUri!!)
                        if (imageList.count() > 0) {
                            create_attention.text = imageList.count().toString()
                            create_attention.visibility = View.VISIBLE
                        }
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