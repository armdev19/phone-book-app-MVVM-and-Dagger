package com.infernal93.phonebookappmvvmanddagger.data.remote

import okhttp3.MultipartBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Armen Mkhitaryan on 16.01.2020.
 */
interface ImagesService {

    @Multipart
    @POST("media")
    fun postImage(@Part photo: MultipartBody.Part): Call<ResponseBody>

}