package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ContactListViewModelFactory @Inject constructor(private val contactListViewModelProvider: Provider<ContactListViewModel>)
    : ViewModelProvider.Factory {

    @Suppress(names = ["UNCHECKED_CAST"])
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return contactListViewModelProvider.get() as T
    }
}