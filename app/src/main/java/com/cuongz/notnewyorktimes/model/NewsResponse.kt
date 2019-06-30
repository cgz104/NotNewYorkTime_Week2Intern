package com.cuongz.notnewyorktimes.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsResponse {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("copyright")
    @Expose
    var copyright: String? = null
    @SerializedName("response")
    @Expose
    var response: Response? = null

}