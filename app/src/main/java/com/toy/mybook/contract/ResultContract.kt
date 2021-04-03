package com.toy.mybook.contract

import com.toy.mybook.api.Item

interface ResultContract {
    interface View{
        fun showToast(message: String)
    }

    interface Present{
        fun setStarBook(book: Item)
    }
}