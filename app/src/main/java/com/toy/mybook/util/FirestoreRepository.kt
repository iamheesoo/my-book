package com.toy.mybook.util

interface FirestoreRepository {
    fun addUserIfNotExists(uid: String)

}