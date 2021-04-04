package com.toy.mybook.contract

import android.net.Uri

interface AccountContract {
    interface View{
        fun setProfile(uri: Any?)
    }

    interface Presenter{
        fun logout()
        fun getProfile()
        fun setProfileOnDB(uri: Uri)
        fun setNickname(name: String)
    }

}