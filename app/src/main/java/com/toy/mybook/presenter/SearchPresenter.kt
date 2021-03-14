package com.toy.mybook.presenter

import android.util.Log
import com.toy.mybook.contract.SearchContract
import com.toy.mybook.model.SearchModel

class SearchPresenter(_view: SearchContract.View) :SearchContract.Present{
    val TAG="SearchPresenter"
    var view: SearchContract.View?=null
    var model: SearchContract.Model?=null

    init{
        view=_view
        model= SearchModel()
    }

    override fun searchBook() {
        Log.i(TAG, "searchBook")
        model?.getSearchResult()
    }

}