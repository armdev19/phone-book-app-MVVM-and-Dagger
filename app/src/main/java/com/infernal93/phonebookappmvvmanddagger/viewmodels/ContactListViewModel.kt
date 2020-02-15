package com.infernal93.phonebookappmvvmanddagger.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.ErrorListener
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class ContactListViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel() {

    private lateinit var mAuth: FirebaseAuth

    var mErrorListener: ErrorListener? = null

    fun insertAllRoom() {
        roomRepository.insertAll()
    }

    fun deleteAllRoom() {
        roomRepository.deleteAll()
    }

    fun getAllContacts(): LiveData<List<ContactsRoom>>{
        return roomRepository.getAllContact()
    }

    fun logOut() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
    }
}

