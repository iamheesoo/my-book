package com.toy.mybook.contract

import com.toy.mybook.DTO.BookDTO

interface HomeContract {
    interface View{
        fun showHeart()
    }

    interface Presenter{
        fun getBookList(): ArrayList<BookDTO>
        fun setHeart(imgUrl: String, title: String)
    }

    interface Model{
        fun getBookList(): ArrayList<BookDTO>
        fun setHeart(imgUrl: String, title: String)
    }
}