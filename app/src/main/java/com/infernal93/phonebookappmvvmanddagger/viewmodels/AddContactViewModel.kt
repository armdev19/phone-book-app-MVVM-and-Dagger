package com.infernal93.phonebookappmvvmanddagger.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.AddContactListener
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 02.02.2020.
 */
class AddContactViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val apiRepository: ApiRepository) : ViewModel() {

    var mFirstName: String = ""
    var mLastName: String = ""
    var mPhone: String = ""
    var mEmail: String = ""
    var mNotes: String = ""


    var mAddContactListener: AddContactListener? = null

    fun uploadImageForDb(imageForDb: String) {
        apiRepository.imageDb(imageForDb)
    }

    private fun updateRoom(contactsRoom: ContactsRoom) {
        roomRepository.update(contactsRoom)
    }

    private fun insertRoom(contactsRoom: ContactsRoom) {
        roomRepository.insert(contactsRoom)
    }

    fun saveContact() {
        when {
            mFirstName.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_name)
            }
            mLastName.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_last_name)
            }
            mPhone.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_phone)
            }
            mPhone.length < 12 -> {
                mAddContactListener?.showError(textResource = R.string.phone_invalid)
            }
            mEmail.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches() -> {
                mAddContactListener?.showError(textResource = R.string.email_invalid)
            }
            mNotes.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_notes)
            }
            else -> {

                val newContactsRoom = ContactsRoom(
                    _id = "", firstName = mFirstName,
                    lastName = mLastName, phone = mPhone,
                    email = mEmail, notes = mNotes, images = apiRepository.imageDB)

                insertRoom(newContactsRoom)

                val newContactsApi = ContactsApi(
                    id = "", firstName = mFirstName, lastName = mLastName,
                    phone = mPhone, email = mEmail, notes = mNotes, images = "")



                apiRepository.uploadNewContact(newContactsApi, newContactsRoom)

                updateRoom(newContactsRoom)
            }
        }
    }

    fun saveImageAndContact(toPath: String) {

        when {
            mFirstName.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_name)
            }
            mLastName.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_last_name)
            }
            mPhone.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_phone)
            }
            mPhone.length < 12 -> {
                mAddContactListener?.showError(textResource = R.string.phone_invalid)
            }
            mEmail.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches() -> {
                mAddContactListener?.showError(textResource = R.string.email_invalid)
            }
            mNotes.trim().isEmpty() -> {
                mAddContactListener?.showError(textResource = R.string.empty_notes)
            }
            else -> {

                val newContactsRoom = ContactsRoom(
                    _id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                    email = mEmail, notes = mNotes, images = apiRepository.imageDB)

                insertRoom(newContactsRoom)

                val newContactsApi = ContactsApi(
                    id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                    email = mEmail, notes = mNotes, images = "")

                apiRepository.uploadNewImageAndContact(toPath, newContactsApi, newContactsRoom)

                updateRoom(newContactsRoom)
            }
        }
    }
}