package com.fikrielg.hadispocket.data.source.remote.model


import com.google.gson.annotations.SerializedName

data class ListHadisFromBookResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)