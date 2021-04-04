package com.toy.mybook.presenter

import android.util.Log
import com.toy.mybook.api.Item
import com.toy.mybook.contract.SearchContract
import com.toy.mybook.model.APIModel

class SearchPresenter(_view: SearchContract.View) :SearchContract.Present{
    val TAG="SearchPresenter"
    val view=_view
    val apiModel=APIModel

    override fun searchBook(query: String) {
        Log.i(TAG, "searchBook")
        val listener=object:APIModel.APIListener{
            override fun onSuccess(message: Any) {
                startActivity(message as ArrayList<Item>)
            }

            override fun onFailure(message: Any) {
                TODO("Not yet implemented")
            }

        }
        apiModel.getSearchResult(query,listener)
    }

    override fun startActivity(bookList: ArrayList<Item>) {
        view.startResultActivity(bookList)
    }

}