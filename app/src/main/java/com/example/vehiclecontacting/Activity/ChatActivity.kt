package com.example.vehiclecontacting.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.lifecycle.Observer
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.InfoRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Repository.WebRepository
import com.example.vehiclecontacting.Web.TalkController.TalkRepository
import com.example.vehiclecontacting.Widget.ChatLeftView
import com.example.vehiclecontacting.Widget.ChatRightView
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONObject

class ChatActivity : BaseActivity() {

    lateinit var userId: String
    lateinit var userPhoto: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initData()
        initWidget()
    }

    private fun initData() {
        userId = intent.getStringExtra("userId")?:""
        userPhoto = intent.getStringExtra("userPhoto")?:""
        chat_detail.removeAllViews()
        val talkStatus = TalkRepository.getTalk(1000, InfoRepository.user!!.id, 1, userId)
        if (talkStatus == StatusRepository.SUCCESS) {
            for (talk in TalkRepository.talkList.reversed()) {
                if (talk.fromId == InfoRepository.user!!.id) {
                    insertRightChat(InfoRepository.user!!.photo, talk.info)
                }
                else{
                    insertLeftChat(userPhoto, talk.info)
                }
            }
        }
        TalkRepository.updateMessage.value = false
        chat_scroll.fullScroll(ScrollView.FOCUS_DOWN)
    }

    private fun initWidget() {
        sendEvent()
        liveEvent()
        closeEvent()
    }

    private fun closeEvent() {
        chat_close.setOnClickListener {
            finish()
        }
    }

    private fun liveEvent() {
        TalkRepository.updateMessage.observe(this, Observer {
            if (it) {
                chat_detail.removeAllViews()
                val talkStatus = TalkRepository.getTalk(1000, InfoRepository.user!!.id, 1, userId)
                if (talkStatus == StatusRepository.SUCCESS) {
                    for (talk in TalkRepository.talkList.reversed()) {
                        if (talk.fromId == InfoRepository.user!!.id) {
                            insertRightChat(InfoRepository.user!!.photo, talk.info)
                        }
                        else{
                            insertLeftChat(userPhoto, talk.info)
                        }
                    }
                }
                TalkRepository.updateMessage.value = false
                chat_scroll.fullScroll(ScrollView.FOCUS_DOWN)
            }
        })
    }

    private fun sendEvent() {
        chat_send.setOnClickListener {
            val json = JSONObject()
            val msg = chat_edit.text.toString()
            if (msg != "") {
                json.put("fromId", InfoRepository.user!!.id)
                json.put("toId", userId)
                json.put("info", msg)
                WebRepository.webClient.send(json.toString())

                insertRightChat(InfoRepository.user!!.photo, msg)
                chat_scroll.fullScroll(ScrollView.FOCUS_DOWN)
            }
            else {
                ToastView(this).show("消息内容不得为空")
            }
        }
    }

    private fun insertRightChat(avt: String, msg: String) {
        val rightParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rightParam.gravity = Gravity.RIGHT
        val rightView = ChatRightView(this, avt, msg)
        rightView.layoutParams = rightParam

        chat_edit.setText("")
        chat_detail.addView(rightView)
    }

    private fun insertLeftChat(avt: String, msg: String) {
        val leftParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        leftParam.gravity = Gravity.LEFT
        val leftView = ChatLeftView(this, avt, msg)
        leftView.layoutParams = leftParam

        chat_edit.setText("")
        chat_detail.addView(leftView)
    }
}