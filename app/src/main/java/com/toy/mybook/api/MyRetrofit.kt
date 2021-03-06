package com.toy.mybook.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

object MyRetrofit {
    private val BASE_URL="https://openapi.naver.com/v1/search/"
    val TAG="MyRetrofit"
    val CONNECT_TIMEOUT_SEC=20000L

    fun create():Retrofit{
        val interceptor=HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client=OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}