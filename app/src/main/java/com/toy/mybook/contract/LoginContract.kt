package com.toy.mybook.contract

import android.content.pm.PackageManager
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface LoginContract {
    interface View{
        fun moveMainPage(user: FirebaseUser?)
    }

    interface Presenter{
        fun signinAndSignUp(email:String, password:String)
        fun signinEmail(email:String, password:String)
//        fun googleLogin()
        fun firebaseAuthWithGoogle(account: GoogleSignInAccount?)
//        fun facebookLogin()
//        fun handleFacebookAccessToken(token: AccessToken?)
        fun printHashKey(packageManager: PackageManager, packageName: String)
    }

    interface Model{
        fun firebaseAuthWithGoogle(account: GoogleSignInAccount?)
        fun addUserIfNotExists(uid: String, email: String)
    }
}