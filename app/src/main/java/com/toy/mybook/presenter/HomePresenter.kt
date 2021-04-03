package com.toy.mybook.presenter

import com.toy.mybook.contract.HomeContract
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.model.FirestoreModel
import com.toy.mybook.model.HomeModel

class HomePresenter(_view: HomeContract.View):HomeContract.Presenter {
    private var view: HomeContract.View =_view
    private val homeModel=HomeModel
    private val storeModel=FirestoreModel

    override fun getBookList(): ArrayList<BookDTO> {
        return homeModel.getBookList()
    }

    override fun setHeart(imgUrl: String, title: String) {
        storeModel.setHeart(imgUrl, title)
        view.showHeart()
    }
}