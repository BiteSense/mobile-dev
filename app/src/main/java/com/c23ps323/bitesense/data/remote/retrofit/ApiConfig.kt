package com.c23ps323.bitesense.data.remote.retrofit

import android.content.Context
import com.c23ps323.bitesense.utils.UserPreference
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(context: Context): ApiService {
            val userPreference = UserPreference(context)
            val interceptor = Interceptor { chain ->
                userPreference.saveUserCookie("id_user=132362637; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyaWQiOjEzMjM2MjYzNywiaWF0IjoxNjg1NzkxMzkzLCJleHAiOjE2ODU4Nzc3OTN9._-iiDqxToS2pZuHdmZtgnoGYbV0e2S692ztpCf2hqRE")
                val cookie = userPreference.getUserCookie()
                val req = chain.request()
                val requestHeader = req.newBuilder()
                    .addHeader(
                        "Cookie",
                        cookie
                    )
                    .build()
                chain.proceed(requestHeader)
            }

            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://962a-2001-448a-7020-b537-b903-1e61-f565-ebe3.ngrok-free.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}