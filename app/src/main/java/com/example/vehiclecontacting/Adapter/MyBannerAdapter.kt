package com.example.vehiclecontacting.Adapter

import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclecontacting.Activity.DiscussActivity
import com.example.vehiclecontacting.Data.BannerInfo
import com.example.vehiclecontacting.R
import com.example.vehiclecontacting.Repository.ActivityCollector
import com.example.vehiclecontacting.Web.DiscussController.DiscussRepository
import com.example.vehiclecontacting.Widget.BannerView
import com.youth.banner.adapter.BannerAdapter

class MyBannerAdapter(images: List<BannerInfo>): BannerAdapter<BannerInfo, MyBannerAdapter.BannerViewHolder>(images) {

    inner class BannerViewHolder(val view: BannerView): RecyclerView.ViewHolder(view)

    override fun onBindView(holder: BannerViewHolder, data: BannerInfo, position: Int, size: Int) {
        if (data.image != null)
            holder.view.setImageResource(data.image)
        else
            holder.view.setImageResource(R.drawable.gp_defaultimg)
        holder.view.setText(data.text)
        holder.view.setOnClickListener {
            DiscussRepository.getFirstDiscuss(30, data.number)
            val discussIntent = Intent(holder.view.context, DiscussActivity::class.java)
            discussIntent.putExtra("ownerComment", DiscussRepository.ownerComment)
            discussIntent.putExtra("firstComments", DiscussRepository.firstCommentList)
            holder.view.context.startActivity(discussIntent)
        }
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val bannerView = BannerView(parent.context)
        bannerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        return BannerViewHolder(bannerView)
    }
}