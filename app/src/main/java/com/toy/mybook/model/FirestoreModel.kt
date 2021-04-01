package com.toy.mybook.model

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class FirestoreModel {
    private val TAG="FirestoreModel"
    val firestore=FirebaseFirestore.getInstance()

    fun getProfile(uid: String): Any?{
        Log.i(TAG, "getProfile")
        var imgUri:Any?=null
        firestore.collection("profileImages").document(uid).addSnapshotListener { value, error ->
            if(value==null) return@addSnapshotListener

            if(value.data!=null){
                imgUri= value.data!!["image"]
            }

        }

        /**
         * 데이터를 받아오기 전에 리턴해버리는 문제
         */
        Log.i(TAG, imgUri.toString())
        return imgUri.toString()
    }

    fun setProfile(uid: String, imgUri: Uri){
        Log.i(TAG, "setProfile")
        var storageRef=FirebaseStorage.getInstance().reference.child("userProfileImages").child(uid)
        storageRef.putFile(imgUri).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }.addOnSuccessListener { uri->
            var map=HashMap<String, Any>()
            map["image"]=uri.toString()
            firestore.collection("profileImages").document(uid).set(map)
        }
    }
}