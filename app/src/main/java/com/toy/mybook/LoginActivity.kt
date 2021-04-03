package com.toy.mybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.toy.mybook.contract.LoginContract
import com.toy.mybook.databinding.ActivityLoginBinding
import com.toy.mybook.presenter.LoginPresent

class LoginActivity : AppCompatActivity(), LoginContract.View {
    lateinit var binding: ActivityLoginBinding
    var auth:FirebaseAuth?=null
    var googleSignInClient: GoogleSignInClient?=null
    var callbackManager:CallbackManager?=null
    val GOOGLE_LOGIN_CODE=9001
    val TAG="LoginActivity"
    var presenter:LoginPresent?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter=LoginPresent(this)

        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        binding.emailLoginButton.setOnClickListener {
            presenter?.signinAndSignUp(binding.emailEdittext.text.toString(), binding.passwordEdittext.text.toString())
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

//        presenter?.printHashKey(packageManager, packageName)
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
                    presenter?.firebaseAuthWithGoogle(account)
                }
            }
            else Log.i(TAG, "result not success")
        }
        else Log.i(TAG, "result is null")
    }

    fun facebookLogin(){
        LoginManager.getInstance()
            .logInWithReadPermissions(this, arrayListOf("email", "public_profile"))

//        presenter?.facebookLogin()

        /**
         * 밑에서부터 지우기
         * 위에꺼는 퍼미션요청이니까 뷰에서 해야함
         */
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


    override fun moveMainPage(user: FirebaseUser?){
        if(user!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

}

