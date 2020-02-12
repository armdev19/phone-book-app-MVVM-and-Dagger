package com.infernal93.phonebookappmvvmanddagger.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.infernal93.phonebookappmvvmanddagger.App
import com.infernal93.phonebookappmvvmanddagger.data.local.ContactDao
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.model.ContactDatabase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 11.01.2020.
 */

class RoomRepository @Inject constructor(app: App, private val apiRepository: ApiRepository) {

    var contactDao: ContactDao
    private var allContacts: LiveData<List<ContactsRoom>>

    init {
        val database: ContactDatabase? = ContactDatabase.getInstance(app.applicationContext)
        contactDao = database!!.contactDao()
        allContacts = contactDao.getAllContact()
    }

    fun insert(contactsRoom: ContactsRoom) {
        AsyncTask.execute{
            contactDao.insert(contactsRoom) }
    }

    fun update(contactsRoom: ContactsRoom) {
        AsyncTask.execute{
            contactDao.update(contactsRoom) }
    }

    fun delete(contactsRoom: ContactsRoom) {
        AsyncTask.execute {
            contactDao.delete(contactsRoom)
        }
    }

    fun insertAll() {
        AsyncTask.execute{
            val disposable = CompositeDisposable()
            disposable.add(apiRepository.getAllContacts()
                .subscribeOn(Schedulers.io())
                .subscribe({response -> contactDao.insertAll(response)

                }, {

                }))
        }
    }

    fun deleteAll() {
        AsyncTask.execute{
            contactDao.deleteAllContacts() }
    }

    fun getAllContact(): LiveData<List<ContactsRoom>> {
        return contactDao.getAllContact()
    }
}

