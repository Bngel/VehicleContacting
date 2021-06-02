package com.example.vehiclecontacting.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.R
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        initWidget()
    }

    private fun initWidget() {
        friends_close.setOnClickListener {
            finish()
        }
    }
}