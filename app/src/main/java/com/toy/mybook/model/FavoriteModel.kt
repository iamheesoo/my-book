package com.toy.mybook.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.contract.FavoriteContract

class FavoriteModel(_presenter:FavoriteContract.Present):FavoriteContract.Model {
    val TAG="FavoriteModel"
    var presenter: FavoriteContract.Present?=null
    var firestore: FirebaseFirestore?=null
    var uid: String?=null
    var favoriteBookList= arrayListOf<BookDTO>()

    init{
        presenter=_presenter
        firestore= FirebaseFirestore.getInstance()
        uid=FirebaseAuth.getInstance().uid
    }

    override fun getFavoriteBook() {
        firestore?.collection("favorites")
            ?.whereEqualTo("uid", uid)?.get()
            ?.addOnSuccessListener{ it
                if(it==null) return@addOnSuccessListener
                for(document in it.documents){
                    Log.i(TAG, document.toString())
                    favoriteBookList.add(document.toObject(BookDTO::class.java)!!)
                }

                Log.i(TAG, "favorite size: "+ favoriteBookList.size)
                /**
                 * presenter->view한테 띄우게 요청
                 */
        }

    }
}