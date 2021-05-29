package com.example.vehiclecontacting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initWidget()
    }

    private fun initWidget() {
        quitEvent()
        adminEvent()
        closeEvent()
    }

    private fun quitEvent() {
        setting_quit.setOnClickListener {
            val intent = Intent()
            intent.putExtra("quit", true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun adminEvent() {
        setting_admin.visibility = View.VISIBLE
        setting_admin.setOnClickListener {
            val adminIntent = Intent(this, AdminActivity::class.java)
            startActivityForResult(adminIntent, ActivityCollector.ACTIVITY_ADMIN)
        }
        /*if (InfoRepository.user!!.vip >= 100)
            setting_admin.visibility = View.VISIBLE
        else
            setting_admin.visibility = View.GONE*/
    }

    private fun closeEvent() {
        setting_close.setOnClickListener {
            finish()
        }
    }
}