package com.toy.mybook

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.toy.mybook.databinding.ActivityLoginBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    var auth:FirebaseAuth?=null
    var googleSignInClient: GoogleSignInClient?=null
    var callbackManager:CallbackManager?=null
    val GOOGLE_LOGIN_CODE=9001
    val TAG="LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        binding.emailLoginButton.setOnClickListener {
            signinAndSignUp()
        }
        binding.googleSignInButton.setOnClickListener {
            googleLogin()
        }
        binding.facebookLoginButton.setOnClickListener {
            facebookLogin()
        }

        var gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient=GoogleSignIn.getClient(this, gso)
        callbackManager=CallbackManager.Factory.create()

//        printHashKey()
    }

    fun signinAndSignUp(){
        auth?.createUserWithEmailAndPassword(binding.emailEdittext.text.toString(), binding.passwordEdittext.text.toString())
            ?.addOnCompleteListener { task->
                if(task.isSuccessful) moveMainPage(task.result?.user)
                else if(task.exception?.message.isNullOrEmpty()) Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                else signinEmail()
            }
    }

    fun signinEmail(){
        auth?.signInWithEmailAndPassword(binding.emailEdittext.text.toString(), binding.passwordEdittext.text.toString())
            ?.addOnCompleteListener { task->
                if(task.isSuccessful) moveMainPage(task.result?.user)
                else Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    fun googleLogin(){
        var signInIntent=googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)

        if(requestCode==GOOGLE_LOGIN_CODE){
            var result= Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result!=null){
                if(result.isSuccess){
                    var account=result.signInAccount
                    firebaseAuthWithGoogle(account)
                }
            }
            else Log.i(TAG, "result not success")
        }
        else Log.i(TAG, "result is null")
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?){
        var credential=GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task->
                if(task.isSuccessful){
                    moveMainPage(task.result?.user)
                }
                else{
                    Toast.makeText(this, task.exception?.message,  Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun facebookLogin(){
        LoginManager.getInstance()
            .logInWithReadPermissions(this, arrayListOf("email", "public_profile"))

        LoginManager.getInstance()
            .registerCallback(callbackManager, object: FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?){
                    handleFacebookAccessToken(result?.accessToken)
                }

                override fun onCancel() {
                    Log.i(TAG, "onCancel")
                }

                override fun onError(error: FacebookException?) {
                    Log.i(TAG, error?.message.toString())
                }
            })
    }

    fun handleFacebookAccessToken(token: AccessToken?) {
        var credential=FacebookAuthProvider.getCredential(token?.token!!)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task->
                if(task.isSuccessful) moveMainPage(task.result?.user)
                else Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }


    fun moveMainPage(user: FirebaseUser?){
        if(user!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    fun printHashKey() {
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

