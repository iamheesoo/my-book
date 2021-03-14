package com.toy.mybook.presenter

import com.toy.mybook.contract.HomeContract
import com.toy.mybook.DTO.BookDTO
import com.toy.mybook.model.HomeModel

class HomePresenter(_view: HomeContract.View):HomeContract.Presenter {
    private var view: HomeContract.View =_view
    private var model: HomeContract.Model=HomeModel()

    override fun getBookList(): ArrayList<BookDTO> {
        return model.getBookList()
    }

    override fun setHeart(imgUrl: String, title: String) {
        model.setHeart(imgUrl, title)
        view.showHeart()
    }
}