package com.toy.mybook.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @GET("book.xml")
    fun getSearchBookList(@Header("X-Naver-Client-Id") cid: String, @Header("X-Naver-Client-Secret") secret: String, @Query("query") query: String, @Query("display") display: Int):Call<MyResponse>
}