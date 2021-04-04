package com.toy.mybook.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.toy.mybook.LoginActivity
import com.toy.mybook.R
import com.toy.mybook.contract.AccountContract
import com.toy.mybook.databinding.FragmentAccountBinding
import com.toy.mybook.model.FirebaseAuthModel
import com.toy.mybook.presenter.AccountPresenter


class AccountFragment : Fragment(),AccountContract.View{
    val TAG="AccountFragment"
    lateinit var binding: FragmentAccountBinding
    val auth=FirebaseAuthModel
    val presenter=AccountPresenter(this)
    val REQUEST_CODE=10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAccountBinding.inflate(LayoutInflater.from(activity), container, false)

        val listMenu=arrayOf("닉네임 변경", "로그아웃")
        val adapter=ArrayAdapter<String>(requireContext(), android.R.layout.simple_expandable_list_item_1, listMenu)
        binding.lv.adapter=adapter
        binding.lv.setOnItemClickListener { parent, view, position, id ->
            when(position){
                2->{
                    activity?.finish()
                    startActivity(Intent(activity, LoginActivity::class.java))
                    presenter.logout()
                }
            }

        }

        presenter.getProfile()
        binding.ivProfile.setOnClickListener { openGallery() }

        return binding.root
    }

    override fun setProfile(uri:Any?) {
        Log.i(TAG, uri.toString())
        if(uri==null){
            Glide.with(this).load(R.drawable.empty).apply(RequestOptions().circleCrop()).into(binding.ivProfile)
        }
        else{
            Glide.with(this).load(uri.toString()).fallback(R.drawable.empty).apply(RequestOptions().circleCrop()).into(binding.ivProfile)
        }

    }

    private fun openGallery(){
        val intent=Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.data!=null){
            var imgUri:Uri=data.data!!
            setProfile(imgUri)
            presenter.setProfileOnDB(imgUri)
        }
    }
}