package com.toy.mybook.view

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toy.mybook.R
import com.toy.mybook.api.Item
import com.toy.mybook.contract.StarContract
import com.toy.mybook.databinding.FragmentStarBinding
import com.toy.mybook.databinding.ItemStarBookBinding
import com.toy.mybook.presenter.StarPresenter

class StarFragment : Fragment(),StarContract.View{
    lateinit var binding: FragmentStarBinding
    private val TAG="StarFragment"
    lateinit var presenter:StarContract.Present
    var bookList= arrayListOf<Item>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStarBinding.inflate(LayoutInflater.from(activity), container, false)
        
        presenter=StarPresenter(this)
        
        binding.recyclerStar.adapter=StarFragmentRecyclerViewAdapter()
        binding.recyclerStar.layoutManager=LinearLayoutManager(context)

        return binding.root
    }

    override fun showBookList(bookList: ArrayList<Item>) {
        Log.i(TAG, bookList.size.toString())
        this.bookList=bookList
        binding.recyclerStar.adapter?.notifyDataSetChanged()
    }

    inner class StarFragmentRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        lateinit var binding:ItemStarBookBinding

        init{
            presenter.getStarBookList()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            binding= ItemStarBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)


            return CustomViewHolder(binding.root)
        }

        override fun getItemCount(): Int {
            return bookList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
           val book=bookList[position]
            Glide.with(holder.itemView.context).load(book.image).error(R.drawable.empty).fallback(R.drawable.empty).into(binding.bookIv)
            binding.bookTvTitle.text=Html.fromHtml(book.title).toString()
            binding.bookTvAuthor.text=book.author
            binding.rating.rating= book.rating!!
            binding.rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if(fromUser){
                    presenter.setRating(book,rating)
                }
            }
            holder.setIsRecyclable(false)
        }

        private inner class CustomViewHolder(view:View):RecyclerView.ViewHolder(view)
    }


}