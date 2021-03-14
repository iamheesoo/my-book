package com.toy.mybook.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.toy.mybook.LoginActivity
import com.toy.mybook.databinding.FragmentAccountBinding

class AccountFragment : Fragment(){
    lateinit var binding: FragmentAccountBinding
    var auth:FirebaseAuth?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAccountBinding.inflate(LayoutInflater.from(activity), container, false)

        auth= FirebaseAuth.getInstance()
        binding.btnLogout.setOnClickListener {
            activity?.finish()
            startActivity(Intent(activity, LoginActivity::class.java))
            auth?.signOut()
        }
        return binding.root
    }
}