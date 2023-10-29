package com.fikrielg.hadispocket.core.model


import com.google.gson.annotations.SerializedName

data class Contents(
    @SerializedName("arab")
    val arab: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("number")
    val number: Int
)