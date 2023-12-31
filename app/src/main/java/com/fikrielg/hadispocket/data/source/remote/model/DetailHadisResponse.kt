package com.fikrielg.hadispocket.data.source.remote.model


import com.google.gson.annotations.SerializedName

data class DetailHadisResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DataXX,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)