package tronku.project.zealicon.Utils

import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import tronku.project.zealicon.R

object AnimUtils {

    fun setTouchEffect(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_UP -> handleAnimation(v, false)
                MotionEvent.ACTION_DOWN -> handleAnimation(v, true)
            }
            true
        }
    }

    private fun handleAnimation(v: View, pressed: Boolean) {
        val shrinkAnim = AnimationUtils.loadAnimation(v.context, R.anim.shrink_view)
        val growAnim = AnimationUtils.loadAnimation(v.context, R.anim.grow_view)

        if (pressed) {
            shrinkAnim.fillAfter = true
            shrinkAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                }

                override fun onAnimationStart(animation: Animation?) {
                    growAnim.cancel()
                }

            })
            v.startAnimation(shrinkAnim)

        } else {
            growAnim.fillAfter = true
            growAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                }

                override fun onAnimationStart(animation: Animation?) {
                    shrinkAnim.cancel()
                }

            })
            v.startAnimation(growAnim)
        }
    }

}