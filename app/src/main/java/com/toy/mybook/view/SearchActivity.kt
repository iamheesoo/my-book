package com.toy.mybook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.toy.mybook.R
import com.toy.mybook.api.Item
import com.toy.mybook.contract.SearchContract
import com.toy.mybook.databinding.ActivitySearchBinding
import com.toy.mybook.presenter.SearchPresenter

class SearchActivity : AppCompatActivity(), SearchContract.View {
    lateinit var binding: ActivitySearchBinding
    val TAG="SearchActivity"
    var presenter: SearchContract.Present?=null
    var isTitleClicked=false
    var isAuthorClicked=false
    var isPublisherClicked=false
    var isISBNClicked=false
    var tagCnt=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter=SearchPresenter(this)

        binding.ivClose.setOnClickListener {
            finish()
        }

        binding.ivSearch.setOnClickListener {
            /**
             * text가지고 API 일반 검색하기
             */
            presenter?.searchBook(binding.editQuery.text.toString())
        }

        binding.editQuery.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                binding.editQuery.hint=""
            }
            else{
                binding.editQuery.hint= R.string.search.toString()
            }
        }

        setTextViewClickListener()

    }

    fun checkTagCnt(): Boolean{
        return tagCnt==0
    }

    fun setTextViewClickListener(){
        binding.tvTitle.setOnClickListener {
            if(!isTitleClicked) {
                if(!checkTagCnt()){
                    Toast.makeText(this, "한 개만 체크해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                binding.tvTitle.background = ContextCompat.getDrawable(this, R.drawable.btn_fill)
                tagCnt++
            }
            else{
                binding.tvTitle.background = ContextCompat.getDrawable(this, R.drawable.btn_blank)
                tagCnt--
            }

            isTitleClicked = !isTitleClicked
        }
        binding.tvAuthor.setOnClickListener {
            if(!isAuthorClicked) {
                if(!checkTagCnt()){
                    Toast.makeText(this, "한 개만 체크해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                binding.tvAuthor.background = ContextCompat.getDrawable(this, R.drawable.btn_fill)
                tagCnt++
            }
            else{
                binding.tvAuthor.background = ContextCompat.getDrawable(this, R.drawable.btn_blank)
                tagCnt--
            }

            isAuthorClicked = !isAuthorClicked
        }
        binding.tvPublisher.setOnClickListener {
            if(!isPublisherClicked) {
                if(!checkTagCnt()){
                    Toast.makeText(this, "하나만 체크해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                binding.tvPublisher.background = ContextCompat.getDrawable(this, R.drawable.btn_fill)
                tagCnt++
            }
            else{
                binding.tvPublisher.background = ContextCompat.getDrawable(this, R.drawable.btn_blank)
                tagCnt--
            }

            isPublisherClicked = !isPublisherClicked
        }
        binding.tvIsbn.setOnClickListener {
            if(!checkTagCnt()){
                Toast.makeText(this, "한 개만 체크해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!isISBNClicked) {
                binding.tvIsbn.background = ContextCompat.getDrawable(this, R.drawable.btn_fill)
                tagCnt++
            }
            else{
                binding.tvIsbn.background = ContextCompat.getDrawable(this, R.drawable.btn_blank)
                tagCnt--
            }

            isISBNClicked = !isISBNClicked
        }
    }

    override fun startResultActivity(bookList: ArrayList<Item>) {
        Log.i(TAG, "startResultActivity")
        val intent= Intent(this, ResultActivity::class.java)
        intent.putExtra("list", bookList)
        startActivity(intent)
        finish()
    }
}