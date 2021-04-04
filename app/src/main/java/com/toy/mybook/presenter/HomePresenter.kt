package com.toy.mybook.presenter

import com.toy.mybook.contract.HomeContract
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.model.FirestoreModel
import com.toy.mybook.model.CrawlingModel

class HomePresenter(_view: HomeContract.View):HomeContract.Presenter {
    private var view: HomeContract.View =_view

    override fun getBookList(): ArrayList<BookDTO> {
        return CrawlingModel.getBookList()
    }

    override fun setHeart(imgUrl: String, title: String) {
        FirestoreModel.setHeart(imgUrl, title)
        view.showHeart()
    }
}