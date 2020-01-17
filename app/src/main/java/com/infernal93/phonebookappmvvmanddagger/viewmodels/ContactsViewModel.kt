package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ContactsViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel() {

    fun insert(contactsRoom: ContactsRoom){
        roomRepository.insert(contactsRoom)
    }

    fun insertAll() {
        roomRepository.insertAll()
    }

    fun deleteAll() {
        roomRepository.deleteAll()
    }

    fun getAllContacts(): LiveData<List<ContactsRoom>>{
        return roomRepository.getAllContact()
    }
}

