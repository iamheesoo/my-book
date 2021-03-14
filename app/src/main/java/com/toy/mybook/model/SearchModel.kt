package com.toy.mybook.model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tickaroo.tikxml.TikXml
import com.toy.mybook.api.MyResponse
import com.toy.mybook.api.MyRetrofit
import com.toy.mybook.api.RetrofitService
import com.toy.mybook.contract.SearchContract
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchModel:SearchContract.Model {
    val TAG="SearchModel"
    override fun getSearchResult() {
        Log.i(TAG, "getSearchResult")
        val retrofit=MyRetrofit.create()
        val service=retrofit.create(RetrofitService::class.java)
        val call=service.getSearchBookList("niX6IwdbN7rCOAAScnlB", "srrMzHc5VU", "위저드 베이커리")

        call.enqueue(object : Callback<MyResponse>{
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                Log.i(TAG, "onResponse")
                
//                val body=response.body()
//                Log.i(TAG, body.toString())
//                Log.i(TAG, body?.channel?.title!!)

            }

            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                Log.i(TAG, "onFailure")
                Log.i(TAG, t.message.toString())
            }
        })
    }
}