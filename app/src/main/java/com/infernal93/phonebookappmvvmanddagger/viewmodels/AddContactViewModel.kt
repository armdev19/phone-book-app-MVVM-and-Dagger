package com.infernal93.phonebookappmvvmanddagger.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.AddContactListener
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 02.02.2020.
 */
class AddContactViewModel @Inject constructor(private val roomRepository: RoomRepository,
                private val apiRepository: ApiRepository): ViewModel() {

    var mFirstName: String = ""
    var mLastName: String = ""
    var mPhone: String = ""
    var mEmail: String = ""
    var mNotes: String = ""

    var mAddContactListener: AddContactListener? = null


    private fun insert(contactsRoom: ContactsRoom){
        roomRepository.insert(contactsRoom)
    }

    fun uploadImageForDb(imageForDb: String) {
        apiRepository.ImageDb(imageForDb)
    }

    fun saveContact() {
        if (mFirstName.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_name)
        } else if (mLastName.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_last_name)
        } else if (mPhone.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_phone)
        } else if (mPhone.length < 12) {
            mAddContactListener?.showError(textResource = R.string.phone_invalid)
        } else if (mEmail.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_email)
        }else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mAddContactListener?.showError(textResource = R.string.email_invalid)
        } else if (mNotes.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_notes)
        } else {

            val newContactsRoom = ContactsRoom(_id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = apiRepository.imageDB)

            insert(newContactsRoom)

            val newContactsApi = ContactsApi(id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = "")

            apiRepository.uploadNewContact(newContactsApi)
        }
    }

    fun saveImageAndContact(toPath: String?) {

        if (mFirstName.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_name)
        } else if (mLastName.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_last_name)
        } else if (mPhone.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_phone)
        } else if (mPhone.length < 12) {
            mAddContactListener?.showError(textResource = R.string.phone_invalid)
        } else if (mEmail.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_email)
        }else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mAddContactListener?.showError(textResource = R.string.email_invalid)
        } else if (mNotes.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_notes)
        } else {

            val newContactsRoom = ContactsRoom(_id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = apiRepository.imageDB)

            insert(newContactsRoom)

            val newContactsApi = ContactsApi(id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = "")

            apiRepository.uploadNewImageAndContact(toPath, newContactsApi)

        }
    }
}