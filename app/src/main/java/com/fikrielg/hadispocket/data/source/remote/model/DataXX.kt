package com.fikrielg.hadispocket.data.source.remote.model


import com.google.gson.annotations.SerializedName

data class DataXX(
    @SerializedName("available")
    val available: Int,
    @SerializedName("contents")
    val contents: Contents,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)