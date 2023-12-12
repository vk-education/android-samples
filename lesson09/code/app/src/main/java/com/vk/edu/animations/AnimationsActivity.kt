package com.vk.edu.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.activity.ComponentActivity
import com.vk.edu.R

class AnimationsActivity : ComponentActivity() {
    private lateinit var circle: View
    private lateinit var square: View
    private lateinit var root: View
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)

        circle = findViewById(R.id.circle)
        square = findViewById(R.id.square)
        root = findViewById(R.id.root_view)

        root.setOnClickListener {
//            valueAnimator()
//            interpolators()
//            objectAnimator()
//            viewPropertyAnimator()
//            animateLayoutChange()
            animateLayoutChange2()
        }
    }

    private fun animateLayoutChange2() {
        val container = findViewById<ViewGroup>(R.id.container)
        val transitionSet = TransitionSet().apply {
            addTransition(Fade())
            addTransition(Slide())
        }
        TransitionManager.beginDelayedTransition(container, transitionSet)
        findViewById<View>(R.id.square_constr_2).visibility = if (!flag) View.VISIBLE else View.GONE
        flag = !flag
    }


    private fun animateLayoutChange() {
        findViewById<View>(R.id.square_constr_2).visibility = if (!flag) View.VISIBLE else View.GONE
        flag = !flag
    }

    private fun viewPropertyAnimator() {
        val alpha = if (flag) 1f else 0f
        val y = if (flag) 0 else root.measuredHeight

        circle
            .animate()
            .translationY(y.toFloat())
            .alpha(alpha)
            .setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    flag = !flag
                }
            })

    }

    private fun objectAnimator() {
        ObjectAnimator.ofFloat(circle, "translationY", root.measuredHeight.toFloat())
            .setDuration(1000)
            .apply {
                repeatMode = ValueAnimator.REVERSE
                repeatCount = 3
            }
            .start()

        ObjectAnimator.ofFloat(square, View.TRANSLATION_Y, root.measuredHeight.toFloat())
            .setDuration(1000)
            .apply {
                repeatMode = ValueAnimator.REVERSE
                repeatCount = 3
            }
            .start()
    }

    private fun valueAnimator() {
        val animator = ValueAnimator.ofFloat(0f, root.measuredHeight.toFloat()).apply {
            addUpdateListener {
                val value = it.animatedValue as Float
                circle.translationY = value
                square.translationY = value
            }
        }
        val animatorAlpha = ValueAnimator.ofFloat(1f, 0f).apply {
            addUpdateListener {
                val value = it.animatedValue as Float
                circle.alpha = value
                square.alpha = value
            }
        }

        val animatorSet = AnimatorSet().apply {
            playTogether(animator, animatorAlpha)
            duration = 1000L
            childAnimations.forEach {
                (it as ValueAnimator).repeatMode = ValueAnimator.REVERSE
                (it as ValueAnimator).repeatCount = 3
            }
        }
        animatorSet.start()
    }


    private fun interpolators() {
        val animator = ValueAnimator.ofFloat(0f, root.measuredHeight.toFloat()).apply {

            interpolator = LinearInterpolator() // default
            addUpdateListener {
                val value = it.animatedValue as Float
                Log.d("debug", "linear ${it.animatedFraction}: $value")
                circle.translationY = value
            }
        }

        val animator2 = ValueAnimator.ofFloat(0f, root.measuredHeight.toFloat()).apply {
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Float
                Log.d("debug", "accelerate ${it.animatedFraction}: $value")
                square.translationY = value
            }
        }

        val animatorSet = AnimatorSet().apply {
            playTogether(animator, animator2)
            duration = 2000
            childAnimations.forEach {
                (it as ValueAnimator).repeatMode = ValueAnimator.REVERSE
                (it as ValueAnimator).repeatCount = 1
            }
        }
        animatorSet.start()
    }
}