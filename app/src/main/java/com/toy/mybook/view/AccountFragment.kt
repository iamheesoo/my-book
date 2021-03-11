package com.toy.mybook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.toy.mybook.databinding.FragmentAccountBinding

class AccountFragment : Fragment(){
    lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAccountBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }
}