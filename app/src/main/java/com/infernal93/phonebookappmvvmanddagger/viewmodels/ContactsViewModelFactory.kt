package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ContactsViewModelFactory @Inject constructor(private val contactsViewModelProvider: Provider<ContactsViewModel>)
    : ViewModelProvider.Factory {

    @Suppress(names = ["UNCHECKED_CAST"])
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return contactsViewModelProvider.get() as T
    }
}