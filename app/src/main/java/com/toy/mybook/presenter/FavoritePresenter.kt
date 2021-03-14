package com.toy.mybook.presenter

import com.toy.mybook.contract.FavoriteContract
import com.toy.mybook.model.FavoriteModel

class FavoritePresenter(_view: FavoriteContract.View): FavoriteContract.Present {
    val TAG="FavoritePresenter"
    var view: FavoriteContract.View?=null
    var model: FavoriteContract.Model?=null

    init{
        view=_view
        model=FavoriteModel(this)
    }

    override fun getFavoriteBook() {
        model?.getFavoriteBook()
//        view?.setFavoriteBook(bookList!!)
        /**
         * view한테 띄우기 요청
         */
    }
}