package ali.khaleghi.batman.util

import android.content.Context
import android.widget.Toast

object Toaster {

    fun show(context: Context?, message: String?) {
        if (message == null) return
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun show(context: Context?, message: String?, length: Int) {
        if (message == null) return
        Toast.makeText(context, message, length).show()
    }

}
