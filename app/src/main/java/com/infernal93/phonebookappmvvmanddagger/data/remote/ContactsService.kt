package com.infernal93.phonebookappmvvmanddagger.data.remote

import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */
interface ContactsService {

    @GET(value = "rest/contacts")
    fun getContactModel(): Single<List<ContactsRoom>>

    @POST(value = "rest/contacts")
    fun postNewContact(@Body contactsApi: ContactsApi): Call<ContactsApi>
}