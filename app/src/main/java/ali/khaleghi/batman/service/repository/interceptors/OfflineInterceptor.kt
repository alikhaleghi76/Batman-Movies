package ali.khaleghi.batman.service.repository.interceptors

import ali.khaleghi.batman.util.AppConfigs
import ali.khaleghi.batman.util.NetworkState
import ali.khaleghi.batman.util.Toaster
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import java.io.IOException

class OfflineInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!NetworkState.isOnline(context)) {

            val maxStale = 60 * 60 * AppConfigs.CACHE_EXPIRE_TIME
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }

        return chain.proceed(request)
    }

}
