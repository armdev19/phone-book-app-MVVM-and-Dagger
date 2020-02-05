package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 02.02.2020.
 */
class AddContactViewModel @Inject constructor(private val roomRepository: RoomRepository): ViewModel() {

    fun insert(contactsRoom: ContactsRoom){
        roomRepository.insert(contactsRoom)
    }
}