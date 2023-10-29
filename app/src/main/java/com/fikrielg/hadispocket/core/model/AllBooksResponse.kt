package com.fikrielg.hadispocket.core.model


import com.google.gson.annotations.SerializedName

data class AllBooksResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)