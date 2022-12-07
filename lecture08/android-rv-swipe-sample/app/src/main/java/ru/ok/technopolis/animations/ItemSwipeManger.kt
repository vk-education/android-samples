package ru.ok.technopolis.animations

import android.content.Context
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import java.util.*

class ItemSwipeManger(
        context: Context,
        private val listener: SwipeListener,
) : OnItemTouchListener, OnChildAttachStateChangeListener {

    private val animations: MutableMap<ViewHolder, DynamicAnimation<*>> = HashMap()
    private val touchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var initialTouchX = 0f
    private var recyclerView: RecyclerView? = null
    private var velocityTracker: VelocityTracker? = null
    private var swipedChild: View? = null

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView

        recyclerView.addOnItemTouchListener(this)
        recyclerView.addOnChildAttachStateChangeListener(this)
    }

    fun detachFromRecyclerView() {
        val recyclerView = recyclerView ?: return

        recyclerView.removeOnItemTouchListener(this)
        recyclerView.removeOnChildAttachStateChangeListener(this)
        for (animation in animations.values) {
            animation.cancel()
        }
        animations.clear()
    }

    override fun onChildViewAttachedToWindow(view: View) {
    }

    override fun onChildViewDetachedFromWindow(view: View) {
        val recyclerView = recyclerView ?: return

        view.translationX = 0f
        recyclerView.getChildViewHolder(view)?.also { animations.remove(it) }
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                initialTouchX = event.x

                velocityTracker?.recycle()
                velocityTracker = VelocityTracker.obtain().apply { addMovement(event) }

                return false
            }
            MotionEvent.ACTION_MOVE -> {
                velocityTracker?.addMovement(event)

                val dragged = event.x - initialTouchX > touchSlop
                if (dragged) {
                    swipedChild = rv.findChildViewUnder(event.x, event.y)
                }

                return dragged
            }
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, event: MotionEvent) {
        val swipedChild = swipedChild ?: return
        val velocityTracker = velocityTracker ?: return

        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> swipedChild.translationX = event.x - initialTouchX
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val swipeViewHolder = rv.findContainingViewHolder(swipedChild) ?: return
                velocityTracker.computeCurrentVelocity(1000)
                val velocity = velocityTracker.xVelocity
                if (velocity > 0) {
                    animateWithFling(swipeViewHolder, velocity)
                } else {
                    animateWithSpring(swipeViewHolder, velocity)
                }
                velocityTracker.clear()
            }
        }
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    private fun animateWithFling(viewHolder: ViewHolder, velocity: Float) {
        val recyclerView = recyclerView ?: return

        val animation = FlingAnimation(swipedChild, DynamicAnimation.TRANSLATION_X)
        animation.friction = 1f
        animation.setStartVelocity(velocity)
        animation.setMaxValue(viewHolder.itemView.width.toFloat())

        viewHolder.setIsRecyclable(false)

        animation.addEndListener { _, _, value, velocity ->
            if (value >= recyclerView.width) {
                viewHolder.setIsRecyclable(true)
                listener.onSwiped(viewHolder)
            } else {
                animateWithSpring(viewHolder, velocity)
            }
        }

        animations[viewHolder] = animation
        animation.start()
    }

    private fun animateWithSpring(viewHolder: ViewHolder, velocity: Float) {
        val animation = SpringAnimation(viewHolder.itemView, DynamicAnimation.TRANSLATION_X)
        animation.setStartVelocity(velocity)

        val springForce = SpringForce(0F)
        springForce.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
        springForce.stiffness = SpringForce.STIFFNESS_LOW
        animation.spring = springForce

        viewHolder.setIsRecyclable(false)

        animation.addEndListener { _, _, _, _ ->
            animations.remove(viewHolder)
            viewHolder.setIsRecyclable(true)
        }

        animations[viewHolder] = animation
        animation.start()
    }

    interface SwipeListener {
        fun onSwiped(viewHolder: ViewHolder)
    }

}