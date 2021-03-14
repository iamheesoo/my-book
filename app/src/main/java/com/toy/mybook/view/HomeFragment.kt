package com.toy.mybook.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toy.mybook.contract.HomeContract
import com.toy.mybook.databinding.FragmentHomeBinding
import com.toy.mybook.databinding.ItemBookBinding
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.presenter.HomePresenter
import java.util.*
import kotlin.concurrent.schedule

class HomeFragment : Fragment(), HomeContract.View{
    lateinit var binding:FragmentHomeBinding
    val TAG:String="HomeFragment"
    var currentUserUid:String?=null
    var presenter:HomePresenter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter= HomePresenter(this)

        binding= FragmentHomeBinding.inflate(LayoutInflater.from(activity), container, false)
        binding.fragHomeRecyclerviewAladin.adapter=HomeFragmentRecyclerviewAdapter()
        binding.fragHomeRecyclerviewAladin.layoutManager=GridLayoutManager(context,2)

        binding.searchIv.setOnClickListener {
            startActivity(Intent(context, SearchActivity::class.java))
        }
        currentUserUid=FirebaseAuth.getInstance().currentUser.uid

        return binding.root
    }

    inner class HomeFragmentRecyclerviewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        lateinit var binding: ItemBookBinding
        var bookList=arrayListOf<BookDTO>()
        var firestore: FirebaseFirestore?=null

        init{
            bookList.clear()
            bookList=presenter?.getBookList()!!
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            binding= ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            firestore= FirebaseFirestore.getInstance()
            Log.i(TAG, "onCreateViewHolder")

            val customViewHolder=CustomViewHolder(binding.root)
            binding.bookIv.setOnLongClickListener {
                val position=customViewHolder.adapterPosition
                presenter?.setHeart(bookList[position].imgUrl, bookList[position].title)
                true
            }
            return customViewHolder
        }

        override fun getItemCount(): Int {
            return bookList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            Log.i(TAG, "onBindViewHolder")

            Glide.with(holder.itemView.context).load(bookList[position].imgUrl).into(binding.bookIv)
            binding.bookTvTitle.text=bookList[position].title
            binding.bookTvRank.text=(position+1).toString()
            holder.setIsRecyclable(false)
        }


        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

    }

    override fun showHeart() {
        binding.imageView.isSelected=true
        binding.imageView.visibility=View.VISIBLE
        binding.imageViewAnimation.likeAnimation()
        Timer().schedule(1500){
            binding.imageView.isSelected=false
            binding.imageView.visibility=View.INVISIBLE
        }
    }
}

