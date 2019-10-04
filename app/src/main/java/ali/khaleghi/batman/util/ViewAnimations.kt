package ali.khaleghi.batman.util

import ali.khaleghi.batman.R
import android.animation.Animator
import android.os.Build
import android.view.View


fun View.fadeOut() {
    if (visibility == View.GONE || tag == "going") return
    tag = "going"
    animate().alpha(0f)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                visibility = View.GONE
                tag = ""
            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}

        })
        .duration = 200
}

fun View.fadeIn() {
    if (visibility == View.VISIBLE) return
    visibility = View.VISIBLE
    animate().alpha(1f)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}

        }).duration = 200
}