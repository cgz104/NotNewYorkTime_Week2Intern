package com.cuongz.notnewyorktimes.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NYTapi {
    companion object{
        private var BASE_URL = "https://api.nytimes.com/svc/search/v2/"

        private fun builder(): Retrofit{
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client())
                .build()
        }

        private fun client(): OkHttpClient{
            return OkHttpClient.Builder().addNetworkInterceptor{chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("api-key", "zDOlQuCV0HseZ1rCvVPiaz07pCAWK5dw")
//                    .addQueryParameter("section", "home")
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()
        }

        fun createService(): ApiMethod{
            return builder().create(ApiMethod::class.java)
        }
    }
}