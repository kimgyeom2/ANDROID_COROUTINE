package com.gy25m.android_coroutine



import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiManager {

    //Authorization 인터셉터
    private val interceptor = okhttp3.Interceptor {
        it.proceed(
            it.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "Basi "
                )
                .build()
        )
    }

    private val builder = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .writeTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .addInterceptor(interceptor)

    val service = Retrofit.Builder()
        .baseUrl("")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(builder.build())
        .build()
        .create(ApiService::class.java)

}