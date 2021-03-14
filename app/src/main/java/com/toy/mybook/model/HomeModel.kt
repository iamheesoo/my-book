package com.toy.mybook.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.contract.HomeContract
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class HomeModel:HomeContract.Model {
    val TAG="HomeModel"

    override fun getBookList(): ArrayList<BookDTO> {
        val baseUrl="https://www.aladin.co.kr/shop/common/wbest.aspx?BranchType=1&start=main"
        val pathImg="div a img.i_cover"
        val pathTitle=" table tbody tr td div.ss_book_list ul li a.bo3 b"
        val pathPublisher=""
        val pathAuthor="table tbody tr td div.ss_book_list ul li a"

        val doc: Document = Jsoup.connect(baseUrl).userAgent("Chrome").timeout(0).get()
        val eleImg: Elements =doc.select(pathImg)
        val eleTitle: Elements =doc.select(pathTitle)
        val eleAuthor: Elements=doc.select(pathAuthor)
        var bookList= arrayListOf<BookDTO>()


//        Log.i(TAG, eleAuthor.text())
        for(i in 0 until eleImg.size){
            bookList.add(
                BookDTO(
                    eleImg[i].attr("src").toString(), eleTitle[i].text(), ""
                )
            )
        }
        Log.i(TAG, "bookList size: "+bookList.size)

        return bookList
    }

    override fun setHeart(imgUrl: String, title: String) {
        Log.i(TAG, "setHeart")
        var uid=FirebaseAuth.getInstance().uid
        var firestore= FirebaseFirestore.getInstance()

        var map= mutableMapOf<String, Any>()
        map["uid"]=uid!!
        map["imgUrl"]=imgUrl
        map["title"]=title
//        var book=BookDTO(imgUrl, title, uid)
//        map["book"]=book!!

        firestore.collection("favorites").document().set(map)
    }

}