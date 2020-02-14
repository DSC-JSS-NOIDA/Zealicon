package tronku.project.zealicon.Utils

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import tronku.project.zealicon.R


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
                    handleGrowAnimation(v, navId, args)
                MotionEvent.ACTION_DOWN -> handleAnimation(v, true)
            }
            true
        }
    }

    private fun handleGrowAnimation(v: View, navId: Int, args: Bundle?) {
        val growAnim = AnimationUtils.loadAnimation(v.context, R.anim.grow_view)
        growAnim.fillAfter = true
        growAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                v.findNavController().navigate(navId, args, null, null)
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        v.startAnimation(growAnim)
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

    fun enterTransition(): Transition? {
        val bounds = ChangeBounds()
        bounds.duration = 400
        return bounds
    }

    fun exitTransition(): Transition? {
        val bounds = ChangeBounds()
        bounds.duration = 400
        return bounds
    }

}