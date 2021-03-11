package com.toy.mybook.contract

import com.toy.mybook.model.BookDTO

interface HomeConstract {
    interface View{
        fun showHeart()
    }

    interface Presenter{
        fun getBookList(): ArrayList<BookDTO>
        fun setHeart(position: Int)
    }

    interface Model{
        fun getBookList(): ArrayList<BookDTO>
        fun setHeart(position: Int)
    }
}