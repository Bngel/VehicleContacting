package com.example.vehiclecontacting.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.AdministratorController.AdministratorRepository
import com.example.vehiclecontacting.Widget.FrozenUserCard
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_froze.*

class FrozeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_froze)
        initData()
        initWidget()
    }

    private fun initData() {
        val status = AdministratorRepository.getFrozenList(1000, 1)
        if (status == StatusRepository.SUCCESS) {
            for (frozenUser in AdministratorRepository.frozenList) {
                val view = FrozenUserCard(this, frozenUser.photo, frozenUser.username, frozenUser.frozenDate, frozenUser.id, frozenUser.vip)
                view.setOnClickListener {
                    val dialog = AlertDialog.Builder(this)
                        .setTitle("提示:")
                        .setMessage("是否确认解封用户 ${frozenUser.username}")
                        .setPositiveButton("确定",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                val reopenStatus = AdministratorRepository.postReopenUser(frozenUser.id)
                                if (reopenStatus == StatusRepository.SUCCESS)
                                    ToastView(this).show("解封 ${frozenUser.username} 成功")
                                else {
                                    ToastView(this).show("解封 ${frozenUser.username} 失败")
                                }
                                AdministratorRepository.frozenList.clear()
                                froze_cards.removeAllViews()
                                initData()
                            })
                        .setNegativeButton("取消",
                            DialogInterface.OnClickListener { dialogInterface, i ->  })
                        .create()
                        .show()
                }
                froze_cards.addView(view)
            }
        }
        else {
            ToastView(this).show("获取被封禁用户数据失败")
        }
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        froze_close.setOnClickListener {
            finish()
        }
    }
}