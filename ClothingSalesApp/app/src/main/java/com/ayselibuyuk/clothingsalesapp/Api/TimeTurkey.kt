package com.ayselibuyuk.clothingsalesapp.Api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimeTurkey {
    @SerializedName("day_of_week")
    @Expose
    var dayOfWeek:String? = null

    @SerializedName("day_of_year")
    @Expose
    var dayOfYear: String? = null
}