package ali.khaleghi.batman.util

import android.content.Context
import android.widget.Toast

object Toaster {

    fun show(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun show(context: Context?, message: String, length: Int) {
        Toast.makeText(context, message, length).show()
    }

}
