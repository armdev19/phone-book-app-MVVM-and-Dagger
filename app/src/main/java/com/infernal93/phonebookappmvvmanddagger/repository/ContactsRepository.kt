package com.infernal93.phonebookappmvvmanddagger.repository

import com.infernal93.phonebookappmvvmanddagger.model.ContactsModel
import com.infernal93.phonebookappmvvmanddagger.remote.ContactsService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ContactsRepository @Inject constructor(private val contactsService: ContactsService) {

    fun modelSingle(): Single<List<ContactsModel>> {

        return contactsService.getContactModel()
    }

}