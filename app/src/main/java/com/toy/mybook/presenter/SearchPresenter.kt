package com.toy.mybook.presenter

import android.util.Log
import com.toy.mybook.api.Item
import com.toy.mybook.contract.SearchContract
import com.toy.mybook.model.ApiModel

class SearchPresenter(_view: SearchContract.View) :SearchContract.Present{
    val TAG="SearchPresenter"
    val view=_view
    val apiModel=ApiModel

    override fun searchBook(query: String) {
        Log.i(TAG, "searchBook")
        val listener=object:ApiModel.ApiListener{
            override fun onSuccess(message: Any) {
                startActivity(message as ArrayList<Item>)
            }

            override fun onFail() {
                TODO("Not yet implemented")
            }

        }
        apiModel.getSearchResult(query,listener)
//        view?.startResultActivity(list!!)

        /**
         * 스레드를 돌려서 결과를 메소드 실행이 끝났을 떄 view 호출
         * or
         * model에서 메소드실행 끝나면 presenter가 view 호출하도록 파라미터로 넘겨주기
         */
    }

    override fun startActivity(bookList: ArrayList<Item>) {
        view.startResultActivity(bookList)
    }

}