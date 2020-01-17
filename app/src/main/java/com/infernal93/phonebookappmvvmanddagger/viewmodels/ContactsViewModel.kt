package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import javax.inject.Inject


/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ContactsViewModel @Inject constructor(private val roomRepository: RoomRepository, private val imagesService: ImagesService) : ViewModel() {

    var mutableLiveData = MutableLiveData<List<ContactsApi>>()

    fun getAllContacts(): LiveData<List<ContactsRoom>>{
        val contacts = roomRepository.getAllContact()

       // mutableLiveData.value = contacts
        return contacts
    }

    fun insert(contactsRoom: ContactsRoom){
        roomRepository.insert(contactsRoom)
    }

    fun insertAll() {
        roomRepository.insertAll()
    }

    fun deleteAll() {
        roomRepository.deleteAll()
    }



//    var mutableLiveData = MutableLiveData<ArrayList<ContactsApi>>()
//
//
//
//    private val disposable = CompositeDisposable()
//
//    lateinit var mutableNewContact: ContactsApi
//
//    fun getNewContact(contactsModel: ContactsApi): ContactsApi {
//        contactsRepository.insert(contactsModel)
//
//        mutableNewContact = contactsModel
//
//        return mutableNewContact
//    }
//
//    fun getContactMutableLiveData(): LiveData<List<ContactsApi>> {
//
//
//        disposable.add(contactsRepository.modelSingle()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeWith(object :  DisposableSingleObserver<List<ContactsApi>>() {
//                override fun onSuccess(t: List<ContactsApi>) {
//                    mutableLiveData.value = t
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//
//            }))
//
//        return mutableLiveData
//    }
}

