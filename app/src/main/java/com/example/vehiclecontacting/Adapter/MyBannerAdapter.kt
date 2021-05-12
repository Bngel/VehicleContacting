package com.example.vehiclecontacting.Adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter

class MyBannerAdapter(images: List<Int>): BannerAdapter<Int, MyBannerAdapter.BannerViewHolder>(images) {

    inner class BannerViewHolder(val view: ImageView): RecyclerView.ViewHolder(view)

    override fun onBindView(holder: BannerViewHolder, data: Int, position: Int, size: Int) {
        holder.view.setImageResource(data)
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }
}