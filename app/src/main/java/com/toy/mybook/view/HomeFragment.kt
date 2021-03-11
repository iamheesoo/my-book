package com.toy.mybook.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toy.mybook.contract.HomeConstract
import com.toy.mybook.databinding.FragmentHomeBinding
import com.toy.mybook.databinding.ItemBookBinding
import com.toy.mybook.model.BookDTO
import com.toy.mybook.presenter.HomePresenter

class HomeFragment : Fragment(), HomeConstract.View{
    lateinit var binding:FragmentHomeBinding
    val TAG:String="HomeFragment"
    var currentUserUid:String?=null

    var presenter:HomePresenter?=null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        presenter= HomePresenter(this)

        binding= FragmentHomeBinding.inflate(LayoutInflater.from(activity), container, false)
        binding.fragHomeRecyclerviewAladin.adapter=HomeFragmentRecyclerviewAdapter()
        binding.fragHomeRecyclerviewAladin.layoutManager=LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        currentUserUid=FirebaseAuth.getInstance().currentUser.uid

        binding.imageView.setOnClickListener {
            if(binding.imageView.isSelected){
                binding.imageView.isSelected=false
            }
            else{
                binding.imageView.isSelected=true
                binding.imageViewAnimation.likeAnimation()

            }
        }


        return binding.root
    }

    fun viewHeart(){
        binding.imageView.visibility=View.VISIBLE
        binding.imageViewAnimation.likeAnimation()
        
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
                presenter?.setHeart(customViewHolder.adapterPosition)
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
        TODO("Not yet implemented")
    }
}

