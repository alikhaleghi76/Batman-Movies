package ali.khaleghi.batman.util

import android.app.Activity
import com.google.android.material.snackbar.Snackbar


object Snacker {

    fun show(activity: Activity, message: String) {
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    fun show(activity: Activity, message: String, length: Int) {
        Snackbar.make(activity.findViewById(android.R.id.content), message, length).show()
    }

}

