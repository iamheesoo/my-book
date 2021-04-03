package com.toy.mybook.model

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

object FireStorageModel {
    private val TAG="FireStorageModel"
    val firestore=FirebaseFirestore.getInstance()

    fun getProfile(uid: String, listener:FireStorageListener){
        Log.i(TAG, "getProfile")
        var imgUri:Any?=null
        firestore.collection("profileImages").document(uid).addSnapshotListener { value, error ->
            if(value==null) return@addSnapshotListener

            if(value.data!=null){
                imgUri= value.data!!["image"]
                listener.onSuccess(imgUri!!)
            }
        }
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


    interface FireStorageListener{
        fun onSuccess(message:Any)
        fun onFailure()
    }
}