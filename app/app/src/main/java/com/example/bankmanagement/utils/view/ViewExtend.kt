package com.example.bankmanagement.utils.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bankmanagement.R

inline fun <reified T : Activity> Activity.openActivity() {
    startActivity(createIntent<T>())
}

inline fun <reified T : Activity> Context.openActivity(extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

inline fun <reified T : Activity> Context.createIntent() = Intent(this, T::class.java)

fun <T> MutableLiveData<T>.notifyValueChange() {
    value = value
}

object ViewExtend {
    fun View.slideUp() {
        this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.anim_slide_up))
    }

    fun View.slideDownFast() {
        val loadAnimation = AnimationUtils.loadAnimation(this.context, R.anim.anim_slide_down)
        loadAnimation.duration = 300
        this.startAnimation(loadAnimation)
    }

    fun View.bumpingAnim() {
        val loadAnimation = AnimationUtils.loadAnimation(this.context, R.anim.anim_scale_left)
        loadAnimation.duration = 300
        this.startAnimation(loadAnimation)
    }

    fun RecyclerView.removeAnimation() {
        this.itemAnimator = null
    }

    fun View.parenClick() {
        if (this.parent is ViewGroup) {
            (this.parent as ViewGroup).performClick()
        }
    }
}

