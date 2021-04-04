package com.toy.mybook.contract

import android.content.Context
import android.net.Uri
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.DTO.ImageDTO

interface RecordContract{
    interface View{
        fun setFavoriteBook(bookList: ArrayList<BookDTO>)
        fun setRecordImage(imageList: ArrayList<ImageDTO>)
        fun setRecordImage(imgDTO: ImageDTO)
    }

    interface Present{
        fun getFavoriteBook()
        fun getRecordImage()
        fun setRecordImage(photoname: String, imgUri: Uri)
    }
}