package com.toy.mybook.DTO

import com.google.firebase.firestore.PropertyName

data class BookDTO(
    @get:PropertyName("imgUrl") var imgUrl: String,
    @get:PropertyName("title") var title: String,
    @get:PropertyName("uid") var uid: String
){
    constructor(): this("", "", "")
}