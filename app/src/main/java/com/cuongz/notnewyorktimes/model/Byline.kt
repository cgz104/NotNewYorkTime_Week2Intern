package com.cuongz.notnewyorktimes.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Byline {

    @SerializedName("original")
    @Expose
    var original: String? = null
    @SerializedName("person")
    @Expose
    var person: List<Person>? = null
    @SerializedName("organization")
    @Expose
    var organization: Any? = null

}