package com.toy.mybook.presenter

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.toy.mybook.DTO.ImageDTO
import com.toy.mybook.contract.RecordContract
import com.toy.mybook.model.FireStorageModel
import com.toy.mybook.model.FirestoreModel

class RecordPresenter(_view: RecordContract.View): RecordContract.Present {
    private val TAG="RecordPresenter"
    private val view=_view
    private val uid=FirebaseAuth.getInstance().uid!!

    override fun getFavoriteBook() {
        val listener=object:FirestoreModel.FirestoreListener{
            override fun onSuccess(message: Any) {
                Log.i(TAG, "onSuccess")
            }

            override fun onFailure(message: Any) {
                Log.i(TAG, "onFailure")
            }
        }
        FirestoreModel.getFavoriteBookList(listener)
//        view?.setFavoriteBook(bookList!!)
        /**
         * view한테 띄우기 요청
         */
    }

    override fun getRecordImage() {
        Log.i(TAG, "getRecordImage")
        val listener=object:FirestoreModel.FirestoreListener{
            override fun onSuccess(message: Any) {
                Log.i(TAG, "onSuccess")
                view.setRecordImage(message as ArrayList<ImageDTO>)
            }

            override fun onFailure(message: Any) {
                Log.i(TAG, "onFailure")
            }

        }
        FirestoreModel.getRecordImage(uid,listener)
    }

    override fun setRecordImage(photoname: String, imgUri: Uri) {
        Log.i(TAG, "setRecordImage")
        val listener=object: FireStorageModel.FireStorageListener{
            override fun onSuccess(message: Any) {
                view.setRecordImage(ImageDTO(null, message as String, uid, null))
            }

            override fun onFailure(message: Any) {
                Log.i(TAG, "onFailure")
            }

        }
        FireStorageModel.setRecordImage(FirebaseAuth.getInstance().uid!!, photoname, imgUri, listener)
    }

}