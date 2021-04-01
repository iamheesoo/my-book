package com.toy.mybook.presenter

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.toy.mybook.contract.AccountContract
import com.toy.mybook.model.FirebaseAuthModel
import com.toy.mybook.model.FirestoreModel

class AccountPresenter(_view: AccountContract.View):AccountContract.Presenter {
    private val TAG="AccountPresenter"
    private val authModel = FirebaseAuthModel()
    private val storemodel=FirestoreModel()
    private val view=_view

    override fun getProfile() {
        Log.i(TAG, "getProfile")
        var imgUri=storemodel.getProfile(FirebaseAuth.getInstance().uid!!)
        Log.i(TAG, imgUri.toString())
        view.setProfile(imgUri)
    }

    override fun setProfileOnDB(uri: Uri) {
        Log.i(TAG, "setProfileOnDB")
        storemodel.setProfile(FirebaseAuth.getInstance().uid!!, uri)
    }

    override fun logout() {
        authModel.logout()
    }
}