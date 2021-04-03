package com.toy.mybook.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toy.mybook.R
import com.toy.mybook.api.Item
import com.toy.mybook.contract.ResultContract
import com.toy.mybook.databinding.ActivityResultBinding
import com.toy.mybook.databinding.ItemResultBookBinding
import com.toy.mybook.presenter.ResultPresenter

class ResultActivity : AppCompatActivity(),ResultContract.View {
    lateinit var binding: ActivityResultBinding
    val TAG="ResultActivity"
    lateinit var bookList:ArrayList<Item> // inner class에서 사용
    lateinit var presenter:ResultContract.Present

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter= ResultPresenter(this)

        binding.recyclerResult.adapter=ResultActivityRecyclerViewAdapter()
        binding.recyclerResult.layoutManager=LinearLayoutManager(this)

        binding.ivClose.setOnClickListener { finish() }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    inner class ResultActivityRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        lateinit var binding: ItemResultBookBinding
        private val TAG="ResultAdapter"

        init{
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

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val book=bookList[position]
            Glide.with(holder.itemView.context).load(book.image).error(R.drawable.empty).fallback(R.drawable.empty).into(binding.bookIv)
            binding.bookTvTitle.text=Html.fromHtml(book.title).toString()
            binding.bookTvAuthor.text=book.author
            binding.bookPublisher.text=book.publisher
            holder.setIsRecyclable(false)
            binding.tvOptions.setOnClickListener{
                val contextThemeWrapper=ContextThemeWrapper(applicationContext,R.style.PopupMenuOverlapAnchor)
                val popup=PopupMenu(contextThemeWrapper,holder.itemView)
                popup.inflate(R.menu.popup)
                popup.gravity=Gravity.RIGHT
                popup.setOnMenuItemClickListener { item->
                    when(item.itemId){
                        R.id.popup_add->{
                            presenter.setStarBook(bookList[position])
                            /**
                             * 즐겨찾기에 등록
                             */
                        }

                    }
                    false
                }
                popup.show()
            }
        }

        private inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view)
    }

}