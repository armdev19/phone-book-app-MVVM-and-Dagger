package com.infernal93.phonebookappmvvmanddagger.repository


import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ApiRepository @Inject constructor(private val contactsService: ContactsService) {

    fun getAllContacts(): Single<List<ContactsRoom>> {
        return contactsService.getContactModel()
    }
}


