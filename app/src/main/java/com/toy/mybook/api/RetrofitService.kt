package com.toy.mybook.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    // 책 상세 검색
    @GET("book_adv.xml")
    fun getSearchBookList(@Header("X-Naver-Client-Id") cid: String, @Header("X-Naver-Client-Secret") secret: String, @Query("d_titl") title: String, @Query("display") display: Int):Call<MyResponse>
}