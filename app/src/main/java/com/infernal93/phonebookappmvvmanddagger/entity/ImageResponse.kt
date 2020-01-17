package com.infernal93.phonebookappmvvmanddagger.entity


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("ids")
    val ids: List<String>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("uploadid")
    val uploadid: String
)
