package com.infernal93.phonebookappmvvmanddagger.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.infernal93.phonebookappmvvmanddagger.App
import com.infernal93.phonebookappmvvmanddagger.data.local.ContactDao
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.model.ContactDatabase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 11.01.2020.
 */

class RoomRepository @Inject constructor(app: App, private val apiRepository: ApiRepository) {

    var contactDao: ContactDao
    private var allContacts: LiveData<List<ContactsRoom>>

    companion object {
        @Volatile
        private var INSTANCE: RoomRepository? = null

        fun getInstance(app: App): RoomRepository {
            return INSTANCE ?: getInstance(app)
        }
    }

    init {
        val database: ContactDatabase? = ContactDatabase.getInstance(app.applicationContext)
        contactDao = database!!.contactDao()
        allContacts = contactDao.getAllContact()
    }

    fun insert(contactsRoom: ContactsRoom) {
        AsyncTask.execute{
            contactDao.insert(contactsRoom) }
    }

    fun insertAll() {
        AsyncTask.execute{
            val disposable = CompositeDisposable()
            disposable.add(apiRepository.getAllContacts()
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<ContactsRoom>>() {
                    override fun onSuccess(t: List<ContactsRoom>) {
                        contactDao.insertAll(t) }
                    override fun onError(e: Throwable) {
                    }
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

