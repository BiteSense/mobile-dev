package com.c23ps323.bitesense.data.remote.retrofit

import android.content.Context
import com.c23ps323.bitesense.BuildConfig
import com.c23ps323.bitesense.utils.UserPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getApiService(context: Context): ApiService {
            val userPreference = UserPreference(context)
            val interceptor = Interceptor { chain ->
                val cookie = userPreference.getUserCookie()
                val req = chain.request()
                val requestHeader = req.newBuilder()
                    .addHeader(
                        "Cookie",
                        cookie,
                    )
                    .build()
                chain.proceed(requestHeader)
            }

            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}