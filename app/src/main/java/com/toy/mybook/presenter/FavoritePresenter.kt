package com.toy.mybook.presenter

import android.util.Log
import com.toy.mybook.contract.FavoriteContract
import com.toy.mybook.model.FirestoreModel

class FavoritePresenter(_view: FavoriteContract.View): FavoriteContract.Present {
    val TAG="FavoritePresenter"
    var view=_view
    var model=FirestoreModel

    override fun getFavoriteBook() {
        val listener=object:FirestoreModel.FirestoreListener{
            override fun onSuccess(message: Any) {
                Log.i(TAG, "onSuccess")
            }

            override fun onFailure(message: Any) {
                Log.i(TAG, "onFailure")
            }
        }
        model.getFavoriteBookList(listener)
//        view?.setFavoriteBook(bookList!!)
        /**
         * view한테 띄우기 요청
         */
    }
}