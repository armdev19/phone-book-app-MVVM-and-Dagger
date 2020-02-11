package com.infernal93.phonebookappmvvmanddagger.data.remote

import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Armen Mkhitaryan on 16.01.2020.
 */
interface ImagesService {

    @Multipart
    @POST(value = "media")
    fun postImage(@Part photo: MultipartBody.Part): Single<Response<ResponseBody>>


    @DELETE(value = "media/{id}")
    fun deleteImage(@Path(value = "id") id: String?): Call<ContactsApi>
}