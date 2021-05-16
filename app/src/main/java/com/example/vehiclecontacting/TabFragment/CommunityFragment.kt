package com.example.vehiclecontacting.TabFragment

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.vehiclecontacting.AnimRepository
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.StatusRepository
import kotlinx.android.synthetic.main.view_communitytitle.*

class CommunityFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        community_recommend.setOnClickListener {
            if (StatusRepository.communityTabStatus != StatusRepository.CommunityTab.RECOMMEND) {
                AnimRepository.playTextEnlargedClickAnim(it as TextView, -1)
                AnimRepository.playTextReducedClickAnim(community_follow, 1)
                StatusRepository.communityTabStatus = StatusRepository.CommunityTab.RECOMMEND
            }
        }
        community_follow.setOnClickListener {
            if (StatusRepository.communityTabStatus != StatusRepository.CommunityTab.FOLLOW) {
                AnimRepository.playTextEnlargedClickAnim(it as TextView, 1)
                AnimRepository.playTextReducedClickAnim(community_recommend, -1)
                StatusRepository.communityTabStatus = StatusRepository.CommunityTab.FOLLOW
            }
        }
    }
}