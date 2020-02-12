package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 08.02.2020.
 */
class DetailsViewModel @Inject constructor(private val apiRepository: ApiRepository,
                                           private val roomRepository: RoomRepository): ViewModel() {

    fun deleteContact(id: String){
        apiRepository.deleteContact(id)
    }

    fun deleteImage(id: String?){
        apiRepository.deleteImage(id)
    }

    fun delete(contactsRoom: ContactsRoom){
        roomRepository.delete(contactsRoom)
    }
}