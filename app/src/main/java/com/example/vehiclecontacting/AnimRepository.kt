package com.example.vehiclecontacting

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageView

object AnimRepository {

    fun startAnim(target: ImageView, afterImg: Int) {
        val animX = ObjectAnimator.ofFloat(target, "scaleX", 1f, 0.9f, 1f)
        animX.duration = 300
        // anim1.interpolator = BounceInterpolator()
        val animY = ObjectAnimator.ofFloat(target, "scaleY", 1f, 0.9f, 1f)
        animY.duration = 300
        // anim2.interpolator = BounceInterpolator()
        val animSet = AnimatorSet()
        animSet.play(animX).with(animY)
        animSet.duration = 300
        animSet.start()
        animSet.addListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                // 切换成功后切换Tab图片
                target.setImageResource(afterImg)
            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

    }
}