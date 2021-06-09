package com.example.vehiclecontacting.Activity

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.BoxController.BoxRepository
import com.example.vehiclecontacting.Widget.BoxDialogView
import com.example.vehiclecontacting.Widget.PushView
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_push.*

class PushActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push)
        initData()
        initWidget()
    }

    private fun initData(){
        push_detail.removeAllViews()
        if (InfoRepository.loginStatus.status) {
            val boxStatus = BoxRepository.getAllBox(1000, InfoRepository.user!!.id, 1)
            if (boxStatus == StatusRepository.SUCCESS) {
                for (box in BoxRepository.boxList) {
                    val view = PushView(this, box.title, box.message)
                    view.setOnClickListener {
                        val dialogView = BoxDialogView(this, box.title, box.message)
                        val dialog = AlertDialog.Builder(this)
                            .setView(dialogView)
                            .setPositiveButton("删除",
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    val deleteStatus = BoxRepository.deleteBoxMessage(InfoRepository.user!!.id, listOf(box.number))
                                    if (deleteStatus == StatusRepository.SUCCESS) {
                                        initData()
                                        ToastView(this).show("删除成功")
                                    }
                                    else
                                        ToastView(this).show("删除失败")
                                })
                            .setNegativeButton("取消", null)
                            .create()
                            .show()
                    }
                    push_detail.addView(view)
                }
            }
        }
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        push_close.setOnClickListener {
            finish()
        }
    }
}