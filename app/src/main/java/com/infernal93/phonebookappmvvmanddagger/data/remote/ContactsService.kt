package com.infernal93.phonebookappmvvmanddagger.data.remote

import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */
interface ContactsService {

    @GET(value = "rest/contacts")
    fun getContactModel(): Single<List<ContactsRoom>>

    @POST(value = "rest/contacts")
    fun postNewContact(@Body contactsApi: ContactsApi): Single<ContactsApi>



    @DELETE(value = "rest/contacts/{id}")
    fun deleteContact(@Path(value = "id") id: String): Call<ContactsApi>

    @FormUrlEncoded
    @PUT(value = "rest/contacts/{id}")
    fun updateContact(
        @Path(value = "id") id: String,
        @Field(value = "firstName") firstName: String,
        @Field(value = "lastName") lastName: String,
        @Field(value = "phone") phone: String,
        @Field(value = "email") email: String,
        @Field(value = "notes") notes: String,
        @Field(value = "images") images: String): Call<ContactsApi>

}