package com.toy.mybook.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

object MyRetrofit {
    private val BASE_URL="https://openapi.naver.com/v1/search/"
    val TAG="MyRetrofit"
    val CONNECT_TIMEOUT_SEC=20000L

    init{
        Log.i(TAG, "init")
    }

    fun create():Retrofit{
        val gson: Gson=GsonBuilder().setLenient().create()
        val interceptor=HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client=OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
//            .callFactory(OkHttpClient.Builder().build())
//            .addConverterFactory(JaxbConverterFactory.create())
//            .addConverterFactory(SimpleXmlConverterFactory.create())
//                .addConverterFactory(
//                        SimpleXmlConverterFactory.createNonStrict(
//                                 Persister( AnnotationStrategy() // important part!
//                                        )
//                        )
//                )
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder()
                        .exceptionOnUnreadXml(false)
//                        .addTypeConverter(String.javaClass, HtmlEscapeStringConverter())
                            .build()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}