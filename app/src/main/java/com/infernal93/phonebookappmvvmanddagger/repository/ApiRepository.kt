package com.infernal93.phonebookappmvvmanddagger.repository

import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ApiRepository @Inject constructor(private val contactsService: ContactsService, private val imagesService: ImagesService) {

    fun modelSingle(): Single<List<ContactsRoom>> {

        return contactsService.getContactModel()
    }





//    fun insert(contactsModel: ContactsApi) {
//        InsertContactAsyncTask().execute(contactsModel)
//    }





//    class InsertContactAsyncTask : AsyncTask<ContactsApi, Unit, Unit>() {
//
//        //private val getNewContact: GetNewContact? = null
//
//        override fun doInBackground(vararg param: ContactsApi?) {
//            //getNewContact?.insert(param)
//        }
//
//    }

    }


