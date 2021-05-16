package com.example.vehiclecontacting

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageView
import android.widget.TextView

object AnimRepository {

    fun playTabBtnClickAnim(target: ImageView, afterImg: Int) {
        val scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1f, 0.9f, 1f)
        scaleX.duration = 300
        // anim1.interpolator = BounceInterpolator()
        val scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1f, 0.9f, 1f)
        scaleY.duration = 300
        // anim2.interpolator = BounceInterpolator()
        val animSet = AnimatorSet()
        animSet.play(scaleX).with(scaleY)
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

    fun playTextEnlargedClickAnim(target: TextView, position: Int) {
        val scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1f, 1.2f)
        scaleX.duration = 100
        // anim1.interpolator = BounceInterpolator()
        val scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1f, 1.2f)
        scaleY.duration = 100
        // anim2.interpolator = BounceInterpolator()
        val positionX = ObjectAnimator.ofFloat(target, "translationX", 0f, position * 20f)
        positionX.duration = 20
        val animSet = AnimatorSet()
        animSet.play(scaleX).with(scaleY).with(positionX)
        animSet.duration = 100
        animSet.start()
        animSet.addListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {

            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

    }

    fun playTextReducedClickAnim(target: TextView, position: Int) {
        val scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1.2f, 1f)
        scaleX.duration = 100
        // anim1.interpolator = BounceInterpolator()
        val scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1.2f, 1f)
        scaleY.duration = 100
        // anim2.interpolator = BounceInterpolator()
        val positionX = ObjectAnimator.ofFloat(target, "translationX", position * 20f, 0f)
        positionX.duration = 20
        val animSet = AnimatorSet()
        animSet.play(scaleX).with(scaleY).with(positionX)
        animSet.duration = 100
        animSet.start()
        animSet.addListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {

            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }
}