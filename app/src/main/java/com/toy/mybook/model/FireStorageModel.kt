package com.toy.mybook.model

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

object FireStorageModel {
    private val TAG="FireStorageModel"

    fun setProfile(uid: String, imgUri: Uri){
        Log.i(TAG, "setProfile")
        var storageRef=FirebaseStorage.getInstance().reference.child("userProfileImages").child(uid)
        storageRef.putFile(imgUri).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }.addOnSuccessListener { uri->
            var map=HashMap<String, Any>()
            map["image"]=uri.toString()
            FirestoreModel.setData(map, "profileImages", uid)
        }
    }

    fun setRecordImage(uid: String, photoname:String, imgUri:Uri, listener: FireStorageListener){
        Log.i(TAG, "setRecordImage")
        var storageRef=FirebaseStorage.getInstance().reference.child("recordImages").child(photoname)
        storageRef.putFile(imgUri).continueWithTask { task:Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }.addOnSuccessListener{ uri->
            var map=HashMap<String, Any>()
            map["uid"]=uid
            map["imgUri"]=uri.toString()
            FirestoreModel.setData(map, "recordImages", null)
            listener.onSuccess(uri.toString())
        }
    }


    interface FireStorageListener{
        fun onSuccess(message:Any)
        fun onFailure(message:Any)
    }
}