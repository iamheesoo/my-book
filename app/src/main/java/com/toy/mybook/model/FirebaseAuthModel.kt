package com.toy.mybook.model

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseAuthModel{
    private val TAG="FirebaseAuthModel"

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        /**
         * Presenter 수정 필요
         */
    }


    fun logout(){
        Log.i(TAG, "logout")
        val auth=FirebaseAuth.getInstance()
        auth.signOut()
    }
}