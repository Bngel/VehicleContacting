package com.example.vehiclecontacting.Activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.AdministratorController.AdministratorRepository
import com.example.vehiclecontacting.Widget.ToastView
import com.example.vehiclecontacting.Widget.VehicleCardView
import com.example.vehiclecontacting.Widget.VehicleDialogView
import kotlinx.android.synthetic.main.activity_license.*

class LicenseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
        initData()
        initWidget()
    }

    private fun initData() {
        val status = AdministratorRepository.getVehicleList(1000, "", 1)
        if (status == StatusRepository.SUCCESS) {
            for (license in AdministratorRepository.vehicleCards) {
                val view = VehicleCardView(this, license.userPhoto, license.username, license.license, license.vip)
                view.setOnClickListener {
                    val dialogView = VehicleDialogView(this, license.vehiclePhoto)
                    val dialog = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setPositiveButton("审核通过",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                val passStatus = AdministratorRepository.postJudgeVehicle(AdministratorRepository.IS_PASS,
                                    license.license)
                                if (passStatus == StatusRepository.SUCCESS) {
                                    ToastView(this).show("审核成功")
                                    license_cards.removeAllViews()
                                    initData()
                                }
                                else {
                                    ToastView(this).show("审核失败")
                                }
                            }
                        )
                        .setNegativeButton("拒绝通过",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                val reason = dialogView.findViewById<EditText>(R.id.vehicleDialog_edit).text.toString()
                                if (reason != "") {
                                    val passStatus = AdministratorRepository.postJudgeVehicle(AdministratorRepository.NOT_PASS,
                                        license.license, reason)
                                    if (passStatus == StatusRepository.SUCCESS) {
                                        ToastView(this).show("审核成功")
                                        license_cards.removeAllViews()
                                        initData()
                                    }
                                    else {
                                        ToastView(this).show("审核失败")
                                    }
                                }
                                else {
                                    ToastView(this).show("拒绝原因请勿为空")
                                }
                            }
                        )
                        .create()
                    dialog.show()
                }
                license_cards.addView(view)
            }
        }
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        license_close.setOnClickListener {
            finish()
        }
    }
}