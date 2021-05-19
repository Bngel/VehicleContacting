package com.example.vehiclecontacting.CommunityFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vehiclecontacting.Data.CardInfo
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Widget.CommunityCardView
import com.example.vehiclecontacting.Widget.ToastView
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.android.synthetic.main.fragment_follow.follow_cards
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment: Fragment() {

    var parentContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        cardEvent()
    }

    private fun cardEvent() {
        val cards = getCards()
        if (parentContext != null) {
            for (card in cards) {
                val view = CommunityCardView(parentContext!!, card.title, card.avt, card.username,card.text,card.img,card.like,card.comment)
                view.setOnClickListener {
                    ToastView(parentContext!!).show(card.title)
                }
                recommend_cards.addView(view)
            }
        }
    }

    private fun getCards(): List<CardInfo> {
        return listOf(
            CardInfo("这是一条文章的标题", ContextCompat.getDrawable(parentContext!!, R.drawable.yw_vip)!!, "这是用户名",
                "这是一篇文章的内容这是一篇文章的内容这是一篇文章的内容这是一篇文章的内容这是一篇文章的内容这是一篇文章的内容这是一篇文章的内容这是一篇文章的内容这是一篇文章的内容",
                ContextCompat.getDrawable(parentContext!!, R.drawable.bk_checkrect)!!, 100, 100)
        )
    }
}