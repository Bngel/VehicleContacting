package com.example.vehiclecontacting.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.VehicleController.VehicleRepository
import com.example.vehiclecontacting.Widget.MyVehicleCardView
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_my_vehicle.*

class MyVehicleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_vehicle)
        initWidget()
        initData()
    }

    private fun initWidget() {
        closeEvent()
        addEvent()
    }

    private fun closeEvent() {
        myVehicle_close.setOnClickListener {
            finish()
        }
    }

    private fun addEvent() {
        myVehicle_add.setOnClickListener {
            if (myVehicle_cards.childCount < 4) {
                val addIntent = Intent(this, AddVehicleActivity::class.java)
                startActivityForResult(addIntent, ActivityCollector.ACTIVITY_ADD_VEHICLE)
            }
            else {
                ToastView(this).show("最多仅支持绑定四个车牌")
            }
        }
    }

    private fun initData() {
        val status = VehicleRepository.getVehicleList(InfoRepository.user!!.id)
        if (status == StatusRepository.SUCCESS) {
            for (vehicle in VehicleRepository.vehicleList) {
                val view = MyVehicleCardView(this, vehicle.license, vehicle.vehicleBrand, vehicle.description)
                view.setOnLongClickListener {
                    val dialog = AlertDialog.Builder(this)
                        .setTitle("提示:")
                        .setMessage("是否确定删除该车牌?")
                        .setPositiveButton("删除",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                if (InfoRepository.loginStatus.status) {
                                    val deleteStatus = VehicleRepository.deleteVehicle(InfoRepository.user!!.id, vehicle.license)
                                    if (deleteStatus == StatusRepository.SUCCESS) {
                                        initData()
                                        ToastView(this).show("删除成功")
                                    }
                                    else {
                                        ToastView(this).show("删除失败")
                                    }
                                }
                                else {
                                    ToastView(this).show("获取登录状态失败")
                                }
                            })
                        .setNegativeButton("取消", null)
                        .create()
                        .show()
                    true
                }
                myVehicle_cards.addView(view)
            }
        }
    }
}