package com.infernal93.phonebookappmvvmanddagger.repository


import com.google.gson.Gson
import com.infernal93.phonebookappmvvmanddagger.App
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.entity.ImageResponse
import com.infernal93.phonebookappmvvmanddagger.view.activities.AddContactActivity
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ApiRepository @Inject constructor(private val contactsService: ContactsService, private val imagesService: ImagesService) {

    fun getAllContacts(): Single<List<ContactsRoom>> {
        return contactsService.getContactModel()
    }

    var imageMediaId: String = ""
    var imageDB: String = ""

    fun ImageDb(Image: String): String {
        imageDB = Image
        return imageDB
    }

    fun uploadNewContactImage(toPath: String?) {

        val file = File(toPath)
        val fileReqBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part: MultipartBody.Part = MultipartBody.Part.
            createFormData("upload", file.name, fileReqBody)

        imagesService.postImage(part).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    // Get Image response Id
                    val gson = Gson()
                    val imageResponse = gson.fromJson(response.body()?.string(), ImageResponse::class.java)
                    imageMediaId  = imageResponse.ids[0]
                }
            }

        })
    }

    fun uploadNewContact(newContactApi: ContactsApi) {
        contactsService.postNewContact(newContactApi).enqueue(object : Callback<ContactsApi> {
            override fun onFailure(call: Call<ContactsApi>, t: Throwable) {}

            override fun onResponse(call: Call<ContactsApi>, response: Response<ContactsApi>) {}
        })
    }

}


