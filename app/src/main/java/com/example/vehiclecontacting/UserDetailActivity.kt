package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vehiclecontacting.Web.UserController.UserRepository
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    var isSelf = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        initData()
        initWidget()
    }

    private fun initWidget() {
        if (isSelf)
            editEvent()
        else
            followEvent()
    }

    private fun followEvent() {
        detail_follow.visibility = View.VISIBLE
    }

    private fun editEvent() {
        detail_edit.visibility = View.VISIBLE
    }

    private fun initData() {
        isSelf = intent.getBooleanExtra("isSelf", false)
    }
}