package com.toy.mybook.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.DTO.ImageDTO
import com.toy.mybook.api.Item

object FirestoreModel{
    private val TAG="FirestoreModel"
    private var firestore=FirebaseFirestore.getInstance()
    private var uid=FirebaseAuth.getInstance().uid

    fun getFavoriteBookList(listener: FirestoreListener){
        getBookList("favorites",listener)
        /**
         * bookList 형태 BookDTO 체크
         */
    }

    fun getStarBookList(listener: FirestoreListener){
        getBookList("stars",listener)
    }

    private fun getBookList(path:String,listener: FirestoreListener) {
        var bookList= arrayListOf<Item>()
        firestore.collection(path)
                .whereEqualTo("uid", uid).get()
                .addOnSuccessListener{ it
                    if(it==null) return@addOnSuccessListener
                    for(document in it.documents){
                        bookList.add(document.toObject(Item::class.java)!!)
                    }
                    Log.i(TAG, "book size: "+ bookList.size)
                    listener.onSuccess(bookList)
                }

    }

    fun getImage(uid: String, listener: FirestoreListener){
        Log.i(TAG, "getImage")
        var imgUri:Any?=null
        firestore.collection("profileImages").document(uid).addSnapshotListener { value, error ->
            if(value==null) return@addSnapshotListener

            if(value.data!=null){
                imgUri= value.data!!["image"]
                listener.onSuccess(imgUri!!)
            }
        }
    }

    fun getRecordImage(uid: String, listener: FirestoreListener){
        Log.i(TAG, "getRecordImage")
        var imageList= arrayListOf<ImageDTO>()
        firestore.collection("recordImages").whereEqualTo("uid",uid)
                .get()
                .addOnSuccessListener {
                    if(it==null) return@addOnSuccessListener
                    for(doc in it.documents){
                        imageList.add(doc.toObject(ImageDTO::class.java)!!)
                    }
                    Log.i(TAG, "image size: "+imageList.size)
                    listener.onSuccess(imageList)
                }
    }

    fun addUserIfNotExists(uid: String, email: String) {
        Log.i(TAG, "addUserIfNotExists")
        firestore.collection("users").document(uid).get()
                .addOnSuccessListener {querySnapshot ->
                    if(querySnapshot.exists()){
                        Log.i(TAG, "uid is already exist")

                    }
                    else{
                        Log.i(TAG, "there is no uid")
                        setUser(uid, email)
                    }
                }
    }

    private fun setUser(uid: String, email: String) {
        Log.i(TAG, "addUserToFirestore")
        val map= mutableMapOf<String, Any>()
        map["email"]=email
        map["nickname"]=""

        firestore.collection("users").document(uid).set(map)
    }

    fun setStarBook(book: Item, listener: FirestoreListener){
        Log.i(TAG, "setStarBook")
        firestore.collection("stars")
                .document(uid+book.isbn.toString())
                .get()
                .addOnSuccessListener {
                    if(it.exists()){
                        listener.onFailure(true)
                    }
                    else{
                        book.uid= uid
                        firestore.collection("stars")
                                .document(uid+book.isbn.toString())
                                .set(book)
                                .addOnSuccessListener {
                                    listener.onSuccess(true)
                                }
                    }

                }

    }


    fun setHeart(imgUrl: String, title: String) {
        Log.i(TAG, "setHeart")
        var map= mutableMapOf<String, Any>()
        map["uid"]=uid!!
        map["imgUrl"]=imgUrl
        map["title"]=title

        firestore.collection("favorites").document().set(map)
    }

    fun setRating(book:Item, rating:Float){
        Log.i(TAG, "setRating")
        var map= mutableMapOf<String, Any>()
        map["rating"]=rating
        firestore.collection("stars").document(uid+book.isbn.toString()).update(map)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.i(TAG, "setRating success")
                    }
                    else{
                        Log.i(TAG, "setRating fail")
                    }
                }
    }

    fun setData(map:HashMap<String, Any>, collectionPath:String, documentPath: String?){
        if(documentPath==null){
            firestore.collection(collectionPath).document().set(map)
        }
        else{
            firestore.collection(collectionPath).document(documentPath).set(map)
        }
    }

    fun setNickname(name: String){
        var map= mutableMapOf<String, Any>()
        map["nickname"]=name
        firestore.collection("users").document(uid!!).update(map)
    }

    interface FirestoreListener{
        fun onSuccess(message: Any)
        fun onFailure(message: Any)
    }
}