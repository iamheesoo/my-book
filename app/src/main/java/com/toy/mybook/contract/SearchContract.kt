package com.toy.mybook.contract

import com.toy.mybook.api.Item

interface SearchContract {
    interface View{
        fun startResultActivity(bookList: ArrayList<Item>)
    }

    interface Present{
        fun searchBook(query: String)
        fun startActivity(bookList: ArrayList<Item>)
    }

    interface Model{
        fun getSearchResult(query: String): ArrayList<Item>
    }
}