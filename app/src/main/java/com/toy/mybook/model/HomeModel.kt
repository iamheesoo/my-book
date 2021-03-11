package com.toy.mybook.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toy.mybook.contract.HomeConstract
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class HomeModel:HomeConstract.Model {
    val TAG="HomeModel"

    override fun getBookList(): ArrayList<BookDTO> {
        val baseUrl="https://www.aladin.co.kr/shop/common/wbest.aspx?BranchType=1&start=main"
        val pathImg="div a img.i_cover"
        val pathTitle=" table tbody tr td div.ss_book_list ul li a.bo3 b"
        val doc: Document = Jsoup.connect(baseUrl).userAgent("Chrome").timeout(0).get()
        val eleImg: Elements =doc.select(pathImg)
        val eleTitle: Elements =doc.select(pathTitle)
        var bookList= arrayListOf<BookDTO>()

        for(i in 0 until eleImg.size){
            bookList.add(BookDTO(eleImg[i].attr("src").toString(), eleTitle[i].text()))
        }
        Log.i(TAG, "bookList size: "+bookList.size)

        return bookList
    }

    override fun setHeart(position: Int) {
        Log.i(TAG, "setHeart")
        var uid=FirebaseAuth.getInstance().uid
        var firestore= FirebaseFirestore.getInstance()
        firestore.collection("users").document().get()
                .addOnSuccessListener {querySnapshot ->
                    if(querySnapshot.exists()){
                        Log.i(TAG, "uid is already exist")

                    }
                    else{
                        Log.i(TAG, "there is no uid")
                        addUserToFirestore(uid, firestore)
                    }
                }
    }

    private fun addUserToFirestore(uid: String?, firestore: FirebaseFirestore) {
        Log.i(TAG, "addUserToFirestore")
        var map= mutableMapOf<String, Any>()
        map["uid"]=uid!!

        firestore.collection("users").add(map)
    }


}