package com.toy.mybook.contract

import com.toy.mybook.DTO.BookDTO

interface FavoriteContract{
    interface View{
        fun setFavoriteBook(bookList: ArrayList<BookDTO>)
    }

    interface Present{
        fun getFavoriteBook()
    }

    interface Model{
        fun getFavoriteBook()
    }
}