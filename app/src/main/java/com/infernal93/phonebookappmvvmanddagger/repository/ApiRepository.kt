package com.infernal93.phonebookappmvvmanddagger.repository

import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.entity.ImageResponse
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.ErrorListener
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.lang.RuntimeException

import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ApiRepository @Inject constructor(private val contactsService: ContactsService,
    private val imagesService: ImagesService) {

    private val mBaseUrl = "https://phonebookapp-683c.restdb.io/media/"
    private val disposable = CompositeDisposable()
    var imageMediaId: String = ""
    var imageDB: String = ""

    var mErrorListener: ErrorListener? = null

    fun imageDb(Image: String) {
        imageDB = Image
    }

    fun getAllContacts(): Single<List<ContactsRoom>> {
        return contactsService.getContactModel()
    }

    fun uploadNewContact(newContactApi: ContactsApi, newRoom: ContactsRoom){
        disposable.add(contactsService.postNewContact(newContactApi)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response -> newRoom._id = response.id
                mErrorListener?.showMessage("Контакт обновлен")

            }, {
                throw RuntimeException("invalid response")
            }))
    }

    fun uploadNewImageAndContact(toPath: String, newContactApi: ContactsApi, newRoom: ContactsRoom) {
        val file = File(toPath)
        val fileReqBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("upload", file.name, fileReqBody)

        disposable.add(imagesService.postImage(part)
                .flatMap { response ->
                    response.run {
                        if (code() == 201) {
                            val gson = Gson()
                            val imageResponse = gson.fromJson(body()?.string(), ImageResponse::class.java)

                            imageMediaId = imageResponse.ids[0]
                            newContactApi.images = "$mBaseUrl${imageMediaId}"
                            contactsService.postNewContact(newContactApi)
                        } else {
                            throw RuntimeException("invalid response")
                        }
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, Throwable::printStackTrace))

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
        disposable.add(imagesService.deleteImage(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {}))
    }

    fun updateNewImageAndContact(id: String, name: String, lastName: String,
                                 phone: String, email: String, notes: String, images: String) {

        val file = File(images)
        val fileReqBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("upload", file.name, fileReqBody)

        disposable.add(imagesService.postImage(part)
            .flatMap { response ->
                response.run {
                    if (code() == 201) {
                        val gson = Gson()
                        val imageResponse = gson.fromJson(body()?.string(), ImageResponse::class.java)
                        imageMediaId = imageResponse.ids[0]
                        val newImage = "$mBaseUrl${imageMediaId}"
                        contactsService.updateContactAndImage(id, name, lastName, phone,
                            email, notes, images = newImage)
                    } else {
                        throw RuntimeException("invalid response")

                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, Throwable::printStackTrace))
    }

    fun updateNewContact(id: String, name: String, lastName: String,
                         phone: String, email: String, notes: String){
        disposable.add(contactsService.updateContact(id, name, lastName, phone, email, notes)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            }))
    }

}


