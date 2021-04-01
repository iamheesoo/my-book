package com.toy.mybook.model

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toy.mybook.contract.LoginContract
import com.toy.mybook.presenter.LoginPresenter

class FirebaseAuthModel(){
    val TAG="FirebaseAuthModel"

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {

        /**
         * Presenter 수정 필요
         */
    }

    fun addUserIfNotExists(uid: String, email: String) {
        Log.i(TAG, "addUserIfNotExists")
        var firestore= FirebaseFirestore.getInstance()
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener {querySnapshot ->
                if(querySnapshot.exists()){
                    Log.i(TAG, "uid is already exist")

                }
                else{
                    Log.i(TAG, "there is no uid")
                    addUserToFirestore(uid, email, firestore)
                }
            }
    }

    private fun addUserToFirestore(uid: String, email: String, firestore: FirebaseFirestore) {
        Log.i(TAG, "addUserToFirestore")
        var map= mutableMapOf<String, Any>()
        map["email"]=email

        firestore.collection("users").document(uid!!).set(map)
    }

    fun logout(){
        Log.i(TAG, "logout")
        val auth=FirebaseAuth.getInstance()
        auth.signOut()
    }
}