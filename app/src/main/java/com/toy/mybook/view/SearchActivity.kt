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
    }


    override fun startResultActivity(bookList: ArrayList<Item>) {
        Log.i(TAG, "startResultActivity")
        val intent= Intent(this, ResultActivity::class.java)
        intent.putExtra("list", bookList)
        startActivity(intent)
        finish()
    }
}