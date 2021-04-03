package com.toy.mybook.presenter

import android.util.Log
import com.toy.mybook.api.Item
import com.toy.mybook.contract.ResultContract
import com.toy.mybook.model.FirestoreModel

class ResultPresenter(_view:ResultContract.View):ResultContract.Present {
    private val TAG="ResultPresenter"
    private val view=_view
    private val storeModel=FirestoreModel

    override fun setStarBook(book: Item) {
        Log.i(TAG, "setStarBook")
        val listener=object :FirestoreModel.FirestoreListener{
            override fun onSuccess(message: Any) {
                Log.i(TAG, "onSuccess")
                if(message as Boolean){
                    view.showToast("등록 성공")
                }
            }

            override fun onFailure(message: Any) {
                Log.i(TAG, "onFailure")
                if(message as Boolean){
                    view.showToast("등록 실패\n이미 등록한 책은 아닌지 확인해주세요")
                }
            }

        }

        storeModel.setStarBook(book,listener)
    }
}