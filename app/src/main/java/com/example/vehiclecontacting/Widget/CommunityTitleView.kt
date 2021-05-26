package com.example.vehiclecontacting.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.vehiclecontacting.Repository.AnimRepository
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.StatusRepository
import kotlinx.android.synthetic.main.view_communitytitle.view.*

class CommunityTitleView(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_communitytitle, this)
        AnimRepository.playTextEnlargedClickAnim(community_recommend, -1)
        StatusRepository.communityTabStatus = StatusRepository.CommunityTab.RECOMMEND
    }
}