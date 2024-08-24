package com.my.moneycounting.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NewsArticle(
    @SerializedName("headline") val headline: String,
    @SerializedName("datetime") val datetime: Long,
    @SerializedName("image") val image: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("url") val url: String
)
