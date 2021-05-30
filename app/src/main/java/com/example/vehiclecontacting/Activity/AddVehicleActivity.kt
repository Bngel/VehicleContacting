package com.example.vehiclecontacting.Activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.vehiclecontacting.Data.VehicleInfo
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Web.VehicleController.VehicleRepository
import com.example.vehiclecontacting.Widget.AddVehicleDialogView
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_add_vehicle.*
import kotlinx.android.synthetic.main.activity_create.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddVehicleActivity : BaseActivity() {
    val vehicleInfo = VehicleInfo("","","","", "")
    private var imageFile: File? = null // 声明File对象
    private var imageUri: Uri? = null // 裁剪后的图片uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vehicle)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        numberEvent()
        descriptionEvent()
        typeEvent()
        modelEvent()
        photoEvent()
        okEvent()
    }

    private fun okEvent() {
        vehicleAdd_ok.setOnClickListener {
            if (vehicleInfo.description != null && vehicleInfo.license != null &&
                vehicleInfo.licensePhoto != null && vehicleInfo.type != null && vehicleInfo.model != null) {
                if (vehicleInfo.description == "" || vehicleInfo.license == "" ||
                    vehicleInfo.licensePhoto == "" || vehicleInfo.type == "" || vehicleInfo.model == "")
                    ToastView(this).show("请填写所有信息")
                else {
                    val status = VehicleRepository.postVehicle(
                        vehicleInfo.description,
                        InfoRepository.user!!.id,
                        vehicleInfo.license,
                        vehicleInfo.licensePhoto,
                        vehicleInfo.type,
                        vehicleInfo.model
                    )
                    if (status == StatusRepository.SUCCESS) {
                        ToastView(this).show("提交车辆审核申请成功")
                        finish()
                    } else {
                        ToastView(this).show("提交车辆审核申请失败")
                    }
                }
            }
        }
    }

    private fun closeEvent() {
        vehicleAdd_close.setOnClickListener {
            finish()
        }
    }

    private fun modelEvent() {
        vehicleAdd_model.setOnClickListener {
            val view = AddVehicleDialogView(this, "车辆型号(如奔驰A8)")
            val modelDialog = AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        vehicleAdd_modelText.text = view.getText()
                        vehicleInfo.model = view.getText()
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialogInterface, i ->  })
                .create()
            modelDialog.show()
        }
    }

    private fun numberEvent() {
        vehicleAdd_licenseNumber.setOnClickListener {
            val view = AddVehicleDialogView(this, "车牌号")
            val numDialog = AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        vehicleAdd_licenseNumberText.text = view.getText()
                        vehicleInfo.license = view.getText()
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialogInterface, i ->  })
                .create()
            numDialog.show()
        }
    }

    private fun descriptionEvent() {
        vehicleAdd_description.setOnClickListener {
            val view = AddVehicleDialogView(this, "车辆描述")
            val dpDialog = AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        vehicleAdd_descriptionText.text = view.getText()
                        vehicleInfo.description = view.getText()
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialogInterface, i ->  })
                .create()
            dpDialog.show()
        }
    }

    private fun typeEvent(){
        vehicleAdd_type.setOnClickListener {
            val view = AddVehicleDialogView(this, "车辆类型")
            val typeDialog = AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确定",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        val text = view.getText().trim().toInt()
                        if (text in 1..4) {
                            vehicleAdd_typeText.text = when(text) {
                                1 -> "油车"
                                2 -> "电动汽车"
                                3 -> "油电混合汽车"
                                4 -> "电动自行车"
                                else -> ""
                            }
                            vehicleInfo.type = text.toString().trim()
                        }
                        else {
                            ToastView(this).show("请输入正确的数值")
                        }
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialogInterface, i ->  })
                .create()
            typeDialog.show()
        }
    }

    private fun photoEvent() {
        vehicleAdd_licensePhoto.setOnClickListener {
            callGallery()
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
            ToastView(this).show("保存图片失败, 请重试")
            return flag
        }
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val status = VehicleRepository.postVehiclePhoto(InfoRepository.user!!.id, part)
        if (status != StatusRepository.SUCCESS) {
            ToastView(this).show("提交申请失败, 请重试")
            flag = false
        }
        return flag
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
                        if (saveImage(imageUri!!)) {
                            vehicleInfo.licensePhoto = VehicleRepository.vehicleUrl
                            vehicleAdd_licensePhotoCheck.visibility = View.VISIBLE
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