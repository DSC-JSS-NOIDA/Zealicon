package tronku.project.zealicon.Utils

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import tronku.project.zealicon.R
import java.lang.Exception


object AnimUtils {

    fun setTouchEffect(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                    handleAnimation(v, false)
                MotionEvent.ACTION_DOWN -> handleAnimation(v, true)
            }
            true
        }
    }

    fun setTouchEffect(view: View, navId: Int, args: Bundle?) {
        view.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_MOVE ->
                    handleGrowAnimation(v)
                MotionEvent.ACTION_DOWN -> handleAnimation(v, true)
            }
            true
        }
    }

    private fun handleGrowAnimation(v: View) {
        val growAnim = AnimationUtils.loadAnimation(v.context, R.anim.grow_view)
        growAnim.duration = 400
        v.startAnimation(growAnim)
    }

    fun setClickAnimation(v: View) {
        val shrinkAnim = AnimationUtils.loadAnimation(v.context, R.anim.shrink_view)
        v.startAnimation(shrinkAnim)
    }

    fun setClickAnimation(v: View, navId: Int, args: Bundle?, duration: Long = 150) {
        val shrinkAnim = AnimationUtils.loadAnimation(v.context, R.anim.shrink_view)
        shrinkAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                handleGrowAnimation(v)
                try {
                    Handler().postDelayed({
                        v.findNavController().navigate(navId, args, null, null)
                    }, duration)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        v.startAnimation(shrinkAnim)
    }

    fun handleAnimation(v: View, pressed: Boolean) {
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