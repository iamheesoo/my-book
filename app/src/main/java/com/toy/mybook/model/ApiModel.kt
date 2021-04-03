package com.toy.mybook.model

import android.util.Log
import com.toy.mybook.api.Item
import com.toy.mybook.api.MyResponse
import com.toy.mybook.api.MyRetrofit
import com.toy.mybook.api.RetrofitService
import com.toy.mybook.contract.SearchContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiModel{
    val TAG="ApiModel"

    fun getSearchResult(query: String, listener: ApiListener) {
        Log.i(TAG, "getSearchResult")
        val retrofit=MyRetrofit.create()
        val service=retrofit.create(RetrofitService::class.java)
        val call=service.getSearchBookList("niX6IwdbN7rCOAAScnlB", "srrMzHc5VU", query, 100)
        var list=arrayListOf<Item>()

        call.enqueue(object : Callback<MyResponse>{
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                Log.i(TAG, "onResponse")
                var result: MyResponse? =response.body()
                Log.i("response size", result?.channel?.item?.size.toString())
                list=result?.channel?.item!!
                Log.i(TAG, list?.size.toString())
                listener.onSuccess(list)
            }

            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                Log.i(TAG, "onFailure")
                Log.i(TAG, t.message.toString())
            }
        })

    }


    interface ApiListener{
        fun onSuccess(message: Any)
        fun onFail()
    }
}