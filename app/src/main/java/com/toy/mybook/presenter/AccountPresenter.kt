package com.toy.mybook.presenter

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.toy.mybook.contract.AccountContract
import com.toy.mybook.model.FirebaseAuthModel
import com.toy.mybook.model.FireStorageModel

class AccountPresenter(_view: AccountContract.View):AccountContract.Presenter {
    private val TAG="AccountPresenter"
    private val authModel = FirebaseAuthModel
    private val storemodel=FireStorageModel
    private val view=_view

    override fun getProfile() {
        Log.i(TAG, "getProfile")
        val listener=object :FireStorageModel.FireStorageListener{
            override fun onSuccess(message: Any) {
                Log.i(TAG, "onSuccess")
                view.setProfile(message.toString())
            }

            override fun onFailure() {
                Log.i(TAG, "onFail")
            }
        }

        storemodel.getProfile(FirebaseAuth.getInstance().uid!!,listener)
    }

    override fun setProfileOnDB(uri: Uri) {
        Log.i(TAG, "setProfileOnDB")
        storemodel.setProfile(FirebaseAuth.getInstance().uid!!, uri)
    }

    override fun logout() {
        authModel.logout()
    }
}