package com.toy.mybook.presenter

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.toy.mybook.R
import com.toy.mybook.contract.LoginContract
import com.toy.mybook.model.LoginModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginPresenter(_view:LoginContract.View):LoginContract.Presenter {
    val TAG="LoginPresenter"
    private var view: LoginContract.View?=null
    private var model: LoginContract.Model?=null
    var auth:FirebaseAuth?=null

    init{
        view=_view
        model= LoginModel(this)
        auth= FirebaseAuth.getInstance()
    }

    override fun signinAndSignUp(email:String, password:String) {
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task->
                if(task.isSuccessful) { // 회원가입은 완료된 상태
                    view?.moveMainPage(task.result?.user)
                }
                else if(task.exception?.message.isNullOrEmpty()) Log.i(TAG, task.exception?.message.toString())
                else signinEmail(email, password)
            }
    }

    override fun signinEmail(email:String, password:String) {
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task->
                if(task.isSuccessful) {
                    model?.addUserIfNotExists(auth?.uid!!, email)
                    view?.moveMainPage(task.result?.user)
                }
                else Log.i(TAG, task.exception?.message.toString())
            }
    }

    override fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        var credential= GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task->
                if(task.isSuccessful){
                    model?.addUserIfNotExists(task.result?.user?.uid!!, account?.email!!)
                    view?.moveMainPage(task.result?.user)
                }
                else{
                    Log.i(TAG, task.exception?.message.toString())
                }
            }
    }

    override fun printHashKey(packageManager: PackageManager, packageName: String) {
        try {
            val info: PackageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }
    }
}