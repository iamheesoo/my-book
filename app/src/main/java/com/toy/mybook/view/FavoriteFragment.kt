package com.toy.mybook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.contract.FavoriteContract
import com.toy.mybook.databinding.FragmentFavoriteBinding
import com.toy.mybook.presenter.FavoritePresenter

class FavoriteFragment:Fragment(), FavoriteContract.View {
    lateinit var binding: FragmentFavoriteBinding
    var presenter: FavoriteContract.Present?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter=FavoritePresenter(this)

        binding= FragmentFavoriteBinding.inflate(LayoutInflater.from(activity), container, false)

        presenter?.getFavoriteBook()
        return binding.root
    }

    override fun setFavoriteBook(bookList: ArrayList<BookDTO>) {

    }
}