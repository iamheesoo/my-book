package com.toy.mybook.presenter

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.toy.mybook.contract.LoginContract
import com.toy.mybook.model.FirebaseAuthModel
import com.toy.mybook.model.FirestoreModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginPresent(_view:LoginContract.View):LoginContract.Present {
    val TAG="LoginPresenter"
    private val view=_view
    private val auth=FirebaseAuth.getInstance()

    override fun signinAndSignUp(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if(task.isSuccessful) { // 회원가입은 완료된 상태
                    view.moveMainPage(task.result?.user)
                }
                else if(task.exception?.message.isNullOrEmpty()) Log.i(TAG, task.exception?.message.toString())
                else signinEmail(email, password)
            }
    }

    override fun signinEmail(email:String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if(task.isSuccessful) {
                    FirestoreModel.addUserIfNotExists(auth.uid!!, email)
                    view.moveMainPage(task.result?.user)
                }
                else Log.i(TAG, task.exception?.message.toString())
            }
    }

    override fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        var credential= GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    FirestoreModel.addUserIfNotExists(task.result?.user?.uid!!, account?.email!!)
                    view.moveMainPage(task.result?.user)
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