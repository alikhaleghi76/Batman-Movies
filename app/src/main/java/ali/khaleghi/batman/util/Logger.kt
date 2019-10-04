package ali.khaleghi.batman.util

import android.content.Context
import android.util.Log
import android.widget.Toast

object Logger {

    fun log(message: String?) {
        if (message == null)
            Log.e(AppConfigs.TAG, "null")
        else
            Log.e(AppConfigs.TAG, message)
    }

    fun loge(message: String?) {
        if (message == null)
            Log.e(AppConfigs.TAG, "null")
        else
            Log.e(AppConfigs.TAG, message)
    }
}
