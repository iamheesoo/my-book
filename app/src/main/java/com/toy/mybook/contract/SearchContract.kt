package com.toy.mybook.contract

interface SearchContract {
    interface View{

    }

    interface Present{
        fun searchBook()
    }

    interface Model{
        fun getSearchResult()
    }
}