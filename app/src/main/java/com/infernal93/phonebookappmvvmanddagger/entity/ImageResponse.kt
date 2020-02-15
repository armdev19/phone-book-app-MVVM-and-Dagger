package com.infernal93.phonebookappmvvmanddagger.entity

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName(value = "ids")
    val ids: List<String>,
    @SerializedName(value = "msg")
    val msg: String,
    @SerializedName(value = "uploadId")
    val uploadId: String
)
