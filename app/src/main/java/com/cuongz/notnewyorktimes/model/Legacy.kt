package com.cuongz.notnewyorktimes.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Legacy {

    @SerializedName("widewidth")
    @Expose
    var widewidth: Int? = null
    @SerializedName("wideheight")
    @Expose
    var wideheight: Int? = null
    @SerializedName("wide")
    @Expose
    var wide: String? = null

}