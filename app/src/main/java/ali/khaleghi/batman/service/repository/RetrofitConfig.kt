package ali.khaleghi.batman.service.repository

import ali.khaleghi.batman.service.repository.interceptors.OfflineInterceptor
import ali.khaleghi.batman.service.repository.interceptors.RewriteResponseInterceptor
import ali.khaleghi.batman.util.AppConfigs
import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitConfig {

    private var retrofitConfig: Retrofit? = null

    private fun getOkHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheSize = AppConfigs.CACHE_SIZE * 1024 * 1024

        val cache = Cache(File(context.cacheDir, "httpcache"), cacheSize.toLong())

        return OkHttpClient.Builder()
            .connectTimeout(AppConfigs.HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(AppConfigs.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConfigs.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(RewriteResponseInterceptor())
            .addInterceptor(OfflineInterceptor(context))
            .cache(cache)
            .build()

    }

    fun getRetrofitConfig(context: Context): Retrofit {

        retrofitConfig = Retrofit.Builder()
            .baseUrl(AppConfigs.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(context))
            .build()

        return retrofitConfig as Retrofit
    }

}
