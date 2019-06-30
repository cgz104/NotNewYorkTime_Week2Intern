package com.cuongz.notnewyorktimes.articleSearch

import android.util.Log
import com.cuongz.notnewyorktimes.api.NYTapi
import com.cuongz.notnewyorktimes.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticleSearchPresenter(val view: InterfaceArticleSearch.View): InterfaceArticleSearch.Presenter {
//    init{
//        view.onSetPresenter(this)
//    }

    override fun searchNews(page: Int?, sort: String?, fq: String?, beginDate: String?, endDate: String?) {
        val mapOption = LinkedHashMap<String, Any>()
        mapOption["page"] = "$page"
        if(sort != null){
            mapOption["sort"] = "$sort"
        }
        if(fq != null){
            mapOption["fq"] = "$fq"
        }
        if(beginDate != null){
            mapOption["begin_date"] = "$beginDate"
        }
        if(endDate != null){
            mapOption["end_date"] = "$endDate"
        }

        NYTapi.createService().getNews(mapOption).enqueue(object : Callback<NewsResponse>{
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.w("test failure", "true")
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val body = response.body()
                if(body != null){
                    val listOfDoc = body.response?.docs
                    if(listOfDoc != null){
                        view.onSuccess(listOfDoc)
                        Log.w("test null", "false")
                    }

                }else{
                    Log.w("test null", "true")
                }
            }

        })
    }
}