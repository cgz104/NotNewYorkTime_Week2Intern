package com.cuongz.notnewyorktimes.articleSearch

import com.cuongz.notnewyorktimes.model.Doc

interface InterfaceArticleSearch {

    interface View{
        fun onSetPresenter(presenter: Presenter)

        fun onSuccess(docs: List<Doc>)

        fun onFailure()
    }

    interface Presenter {
        fun searchNews(page: Int?, sort: String?, fq: String?, beginDate: String?, endDate: String?, q: String?)
    }
}