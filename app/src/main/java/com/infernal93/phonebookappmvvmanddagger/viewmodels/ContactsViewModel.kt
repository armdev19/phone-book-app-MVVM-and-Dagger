package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.model.ContactsModel
import com.infernal93.phonebookappmvvmanddagger.repository.ContactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ContactsViewModel @Inject constructor(private val contactsRepository: ContactsRepository) :
    ViewModel() {

    var mutableLiveData = MutableLiveData<List<ContactsModel>>()
    private val disposable = CompositeDisposable()


    fun getContactMutableLiveData(): MutableLiveData<List<ContactsModel>> {

        disposable.add(contactsRepository.modelSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<ContactsModel>>() {
                override fun onSuccess(t: List<ContactsModel>) {
                    mutableLiveData.value = t
                }
                override fun onError(e: Throwable) {
                }
            }))
        return mutableLiveData
    }

}