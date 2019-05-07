package id.test.kostbenhil.shared

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkhttpSingleton {
    lateinit var mInstance: OkHttpClient

    fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()

            builder.addHeader("Content-Type", "application/json")
            chain.proceed(request)
        }
    }

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)!!

    fun getInstance(context: Context): OkHttpClient {

//        var cache: Cache? = null
//        cache = Cache(cacheFile, (10 * 1024 * 1024).toLong())

        mInstance = OkHttpClient()
        mInstance = mInstance.newBuilder()
            .addInterceptor(getInterceptor())
            .addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(ChuckInterceptor(context))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return mInstance
    }

}