package com.toy.mybook.contract

import com.toy.mybook.api.Item

interface StarContract {
    interface View{
        fun showBookList(bookList: ArrayList<Item>)
    }

    interface Present{
        fun getStarBookList()
        fun setRating(book:Item, rating:Float)
    }
}