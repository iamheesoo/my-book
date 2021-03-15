package com.toy.mybook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toy.mybook.api.Item
import com.toy.mybook.databinding.ActivityResultBinding
import com.toy.mybook.databinding.ItemResultBookBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    val TAG="ResultActivity"
    lateinit var bookList:ArrayList<Item> // inner class에서 사용

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerResult.adapter=ResultActivityRecyclerViewAdapter()
        binding.recyclerResult.layoutManager=LinearLayoutManager(this)

        binding.ivClose.setOnClickListener { finish() }
        binding.ivSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
            /**
             * Search화면에서 재검색하면 현재 액티비티를 finish해야함
             */
        }
    }

    inner class ResultActivityRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        lateinit var binding: ItemResultBookBinding
        val TAG="ResultAdapter"

        init{
            /**
             * SearchActivity에서 Result로 넘어올 때, Intent로 list를 넘겨주기
             * SearchModel의 List<Item>받아와서 booklist에 저장
             */
            Log.i(TAG, "init")
            bookList= intent.getSerializableExtra("list") as ArrayList<Item>
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            binding=ItemResultBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val customViewHolder=CustomViewHolder(binding.root)
            binding.layoutBook.setOnClickListener{
                /**
                 * 책 상세정보 액티비티 호출
                 */
            }
            return customViewHolder
        }

        override fun getItemCount(): Int {
            return bookList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val book=bookList[position]
            Glide.with(holder.itemView.context).load(book.image).into(binding.bookIv)
            binding.bookTvTitle.text=book.title
            binding.bookTvAuthor.text=book.author
            binding.bookPublisher.text=book.publisher
            holder.setIsRecyclable(false)
        }

        inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view)
    }
}