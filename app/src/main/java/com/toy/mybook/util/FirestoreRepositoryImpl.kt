package com.toy.mybook.util

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single
import io.reactivex.SingleEmitter

class FirestoreRepositoryImpl : FirestoreRepository{
    val TAG="FirestoreRepositoryImpl"

    override fun addUserIfNotExists(uid: String) {
//        Single.create{
            var firestore=FirebaseFirestore.getInstance()
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener {querySnapshot ->
                    if(querySnapshot.exists()){
                        Log.i(TAG, "uid is already exist")
//                        it.onSuccess(true)

                    }
                    else{
                        Log.i(TAG, "there is no uid")
                        addUserToFirestore(uid, firestore)
                    }
                }
//        }
    }

    private fun addUserToFirestore(uid: String, firestore: FirebaseFirestore) {
        var map= mutableMapOf<String, Any>()
        map["uid"]= uid

        firestore.collection("users").add(map)
    }

}