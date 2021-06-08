package com.example.vehiclecontacting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.TalkController.TalkRepository
import com.example.vehiclecontacting.Web.UserController.UserRepository
import com.example.vehiclecontacting.Widget.ChatBoxView
import kotlinx.android.synthetic.main.activity_chat_box.*

class ChatBoxActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_box)
        initData()
        initWidget()
    }

    private fun initData() {
        chatBox_infos.removeAllViews()
        if (InfoRepository.loginStatus.status) {
            val boxStatus = TalkRepository.getTalkList(1000, InfoRepository.user!!.id, 1)
            if (boxStatus == StatusRepository.SUCCESS) {
                for (chat in TalkRepository.talkBoxList) {
                    val view = ChatBoxView(this, chat.photo, chat.username, chat.lastMessage)
                    view.setOnClickListener {
                        val chatIntent = Intent(this, ChatActivity::class.java)
                        chatIntent.putExtra("userId", chat.id)
                        chatIntent.putExtra("userPhoto", chat.photo)
                        startActivityForResult(chatIntent, ActivityCollector.ACTIVITY_CHAT)
                    }
                    chatBox_infos.addView(view)
                }
            }
        }
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        chatBox_close.setOnClickListener {
            finish()
        }
    }
}