package com.example.vehiclecontacting.Adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclecontacting.Data.BannerInfo
import com.example.vehiclecontacting.Widget.BannerView
import com.youth.banner.adapter.BannerAdapter

class MyBannerAdapter(images: List<BannerInfo>): BannerAdapter<BannerInfo, MyBannerAdapter.BannerViewHolder>(images) {

    inner class BannerViewHolder(val view: BannerView): RecyclerView.ViewHolder(view)

    override fun onBindView(holder: BannerViewHolder, data: BannerInfo, position: Int, size: Int) {
        holder.view.setImageResource(data.image)
        holder.view.setText(data.text)
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val bannerView = BannerView(parent.context)
        bannerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        return BannerViewHolder(bannerView)
    }
}