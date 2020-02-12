package com.infernal93.phonebookappmvvmanddagger.repository


import com.google.gson.Gson
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.entity.ImageResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ApiRepository @Inject constructor(private val contactsService: ContactsService,
    private val imagesService: ImagesService) {

    fun getAllContacts(): Single<List<ContactsRoom>> {
        return contactsService.getContactModel()
    }

    var imageMediaId: String = ""
    var imageDB: String = ""
    var newImageDB: String = ""

    fun ImageDb(Image: String): String {
        imageDB = Image
        return imageDB
    }

    fun NewImageDb(Image: String): String {
        newImageDB = Image
        return newImageDB
    }

    fun uploadNewContact(newContactApi: ContactsApi){
        val disposable = CompositeDisposable()
        disposable.add(contactsService.postNewContact(newContactApi)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            }))
    }

    fun uploadNewImageAndContact(toPath: String?, newContactApi: ContactsApi) {
        val file = File(toPath)
        val fileReqBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("upload", file.name, fileReqBody)

        val disposable = CompositeDisposable()
        disposable.add(imagesService.postImage(part)
                .flatMap { response ->
                    response.run {
                        if (code() == 201) {
                            val gson = Gson()
                            val imageResponse = gson.fromJson(body()?.string(), ImageResponse::class.java)
                            imageMediaId = imageResponse.ids[0]
                            newContactApi.images = "https://phonebookapp-683c.restdb.io/media/${imageMediaId}"
                            contactsService.postNewContact(newContactApi)
                        } else {
                            throw RuntimeException("invalid response")
                        }
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, Throwable::printStackTrace))

    }

    fun deleteContact(id: String) {
        val disposable = CompositeDisposable()
        disposable.add(contactsService.deleteContact(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            }))
    }

    fun deleteImage(id: String?) {
        val disposable = CompositeDisposable()
        disposable.add(imagesService.deleteImage(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {}))
    }

    fun updateContact(id: String, name: String, lastName: String,
        phone: String, email: String, notes: String, images: String) {
        contactsService.updateContact(id, name, lastName, phone, email, notes, images)
            .enqueue(object : Callback<ContactsApi> {
                override fun onFailure(call: Call<ContactsApi>, t: Throwable) {

                }

                override fun onResponse(call: Call<ContactsApi>, response: Response<ContactsApi>) {

                }

            })
    }

}


