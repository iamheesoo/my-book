package com.toy.mybook.presenter

import android.util.Log
import com.toy.mybook.api.Item
import com.toy.mybook.contract.StarContract
import com.toy.mybook.model.FirestoreModel

class StarPresenter(_view:StarContract.View) : StarContract.Present{
    private val TAG="StarPresenter"
    private val view=_view
    private val storeModel=FirestoreModel

    override fun getStarBookList() {
        val listener=object:FirestoreModel.FirestoreListener{
            override fun onSuccess(message: Any) {
                view.showBookList(message as ArrayList<Item>)
            }

            override fun onFailure(message: Any) {
               Log.i(TAG, "onFailure")
            }
        }
        storeModel.getStarBookList(listener)
    }

    override fun setRating(book: Item, rating: Float) {
        storeModel.setRating(book,rating)
    }

}