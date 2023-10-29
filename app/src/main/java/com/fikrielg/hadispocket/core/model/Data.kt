package com.fikrielg.hadispocket.core.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("available")
    val available: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)