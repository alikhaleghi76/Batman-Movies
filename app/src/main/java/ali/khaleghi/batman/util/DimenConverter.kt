package ali.khaleghi.batman.util

import android.content.Context
import android.util.DisplayMetrics

object DimenConverter {

    fun convertDpToPixel(dp: Float, context: Context?): Float {
        if (context == null) return 0f
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Float, context: Context?): Float {
        if (context == null) return 0f
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
