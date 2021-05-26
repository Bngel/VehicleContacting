package com.example.vehiclecontacting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.FollowView
import com.example.vehiclecontacting.Widget.UserCardView
import kotlinx.android.synthetic.main.activity_follow.*
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.android.synthetic.main.view_usercard.*

class FollowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)
        initWidget()


        follow_follows.addView(UserCardView(this, ))
    }

    private fun initWidget() {
        followShowEvent()
    }

    private fun followShowEvent() {
        for (follow in UserRepository.followList) {
            val view = UserCardView(this, follow.id, follow.photo, follow.username, follow.introduction)
            val followBtn = view.findViewById<FollowView>(R.id.userCard_follow)
            followBtn.setStatus(UserRepository.FOLLOW_ED)
            followBtn.follow()
            follow_follows.addView(view)
        }
    }
}