package com.infernal93.phonebookappmvvmanddagger.remote

import com.infernal93.phonebookappmvvmanddagger.model.ContactsModel
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */
interface ContactsService {

    @GET("contacts")
    fun getContactModel(): Single<List<ContactsModel>>
}