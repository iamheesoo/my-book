package com.toy.mybook.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.contract.HomeContract
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

object CrawlingModel {
    private val TAG="CrawlingModel"

    fun getBookList(): ArrayList<BookDTO> {
        val baseUrl="https://www.aladin.co.kr/shop/common/wbest.aspx?BranchType=1&start=main"
        val pathImg="div a img.i_cover"
        val pathTitle=" table tbody tr td div.ss_book_list ul li a.bo3 b"

        val doc: Document = Jsoup.connect(baseUrl).userAgent("Chrome").timeout(0).get()
        val eleImg: Elements =doc.select(pathImg)
        val eleTitle: Elements =doc.select(pathTitle)
        var bookList= arrayListOf<BookDTO>()


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

}