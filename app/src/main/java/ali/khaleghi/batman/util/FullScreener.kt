package ali.khaleghi.batman.util

import android.app.Activity
import android.os.Build.VERSION.SDK_INT
import android.view.View

object FullScreener {
    fun setFullScreen(activity: Activity?) {
        val decorView = activity?.window?.decorView
        if (SDK_INT < 19) {
            decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            or View.SYSTEM_UI_FLAG_FULLSCREEN) // hide status bar
        } else {
            decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE) // hide status bar
        }
    }
}
