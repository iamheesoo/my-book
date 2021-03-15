package com.toy.mybook.presenter

import android.util.Log
import com.toy.mybook.api.Item
import com.toy.mybook.contract.SearchContract
import com.toy.mybook.model.SearchModel

class SearchPresenter(_view: SearchContract.View) :SearchContract.Present{
    val TAG="SearchPresenter"
    var view: SearchContract.View?=null
    var model: SearchContract.Model?=null

    init{
        view=_view
        model= SearchModel(this)
    }

    override fun searchBook(query: String) {
        Log.i(TAG, "searchBook")
        val list=model?.getSearchResult(query)
//        view?.startResultActivity(list!!)

        /**
         * 스레드를 돌려서 결과를 메소드 실행이 끝났을 떄 view 호출
         * or
         * model에서 메소드실행 끝나면 presenter가 view 호출하도록 파라미터로 넘겨주기
         */
    }

    override fun startActivity(bookList: ArrayList<Item>) {
        view?.startResultActivity(bookList)
    }

}