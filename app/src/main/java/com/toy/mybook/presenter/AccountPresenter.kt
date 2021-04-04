package com.toy.mybook.presenter

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.toy.mybook.contract.AccountContract
import com.toy.mybook.model.FirebaseAuthModel
import com.toy.mybook.model.FireStorageModel
import com.toy.mybook.model.FirestoreModel

class AccountPresenter(_view: AccountContract.View):AccountContract.Presenter {
    private val TAG="AccountPresenter"
    private val view=_view

    override fun getProfile() {
        Log.i(TAG, "getProfile")
        val listener=object :FirestoreModel.FirestoreListener{
            override fun onSuccess(message: Any) {
                Log.i(TAG, "onSuccess")
                view.setProfile(message.toString())
            }

            override fun onFailure(message: Any) {
                Log.i(TAG, "onFailure")
            }
        }

        FirestoreModel.getImage(FirebaseAuth.getInstance().uid!!, listener)
    }

    override fun setProfileOnDB(uri: Uri) {
        Log.i(TAG, "setProfileOnDB")
        FireStorageModel.setProfile(FirebaseAuth.getInstance().uid!!, uri)
    }

    override fun logout() {
        FirebaseAuthModel.logout()
    }
}