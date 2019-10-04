package ali.khaleghi.batman.service.repository.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class RewriteResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")

        return if (cacheControl == null || cacheControl.contains("no-store") ||
            cacheControl.contains("no-cache") || cacheControl.contains("must-revalidate") ||
            cacheControl.contains("max-age=0")) {
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 5000)
                .removeHeader("Pragma")
                .build()
        } else {
            originalResponse
        }
    }
}
