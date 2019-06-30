package com.cuongz.notnewyorktimes.api

import com.cuongz.notnewyorktimes.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiMethod {
    @GET("articlesearch.json")
    fun getNews(
        @QueryMap option: LinkedHashMap<String, Any>
    ): Call<NewsResponse>

}

//        @Query("begin_date")
//        begin_date: String?,
//        @Query("end_date")
//        end_date: String?,
//        @Query("facet")
//        facet: String?,
//        @Query("facet_fields")
//        facet_fields: String?,
//        @Query("facet_filter")
//        facet_filter: String?,
//        @Query("fl")
//        fl: String?,
//        @Query("fq")
//        fq: String?,
//        @Query("page")
//        page: Int?,
//        @Query("q")
//        q: String?,
//@Query("sort")
//sort: String