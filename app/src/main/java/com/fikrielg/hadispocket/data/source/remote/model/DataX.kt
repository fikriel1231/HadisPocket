package com.fikrielg.hadispocket.data.source.remote.model


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("available")
    val available: Int,
    @SerializedName("hadiths")
    val hadiths: List<Hadith>,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("requested")
    val requested: Int
)