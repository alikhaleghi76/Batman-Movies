package ali.khaleghi.batman.util

import android.content.Context
import android.util.Log
import android.widget.Toast

object Logger {

    fun log(message: String) {
        Log.v(AppConfigs.TAG, message)
    }

    fun loge(message: String) {
        Log.e(AppConfigs.TAG, message)
    }
}
