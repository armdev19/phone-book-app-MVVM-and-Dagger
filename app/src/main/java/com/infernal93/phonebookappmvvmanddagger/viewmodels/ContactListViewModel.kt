package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import com.infernal93.phonebookappmvvmanddagger.repository.AuthRepository
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ContactListViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val authRepository: AuthRepository,
    private val apiRepository: ApiRepository) : ViewModel() {

    fun getAllContacts(): LiveData<List<ContactsRoom>> {
        return roomRepository.getAllContact()
    }

    fun insertAllRoom() {
        roomRepository.insertAll()
    }

    fun deleteAllRoom() {
        roomRepository.deleteAll()
    }

    fun logOut() {
        authRepository.signOut()
    }
}

