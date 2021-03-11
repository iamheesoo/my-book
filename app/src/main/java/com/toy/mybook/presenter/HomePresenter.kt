package com.toy.mybook.presenter

import com.toy.mybook.contract.HomeConstract
import com.toy.mybook.model.BookDTO
import com.toy.mybook.model.HomeModel

class HomePresenter(_view: HomeConstract.View):HomeConstract.Presenter {
    private var view: HomeConstract.View =_view
    private var model: HomeConstract.Model=HomeModel()

    override fun getBookList(): ArrayList<BookDTO> {
        return model.getBookList()
    }

    override fun setHeart(position: Int) {
        model.setHeart(position)
        view.showHeart()
    }
}