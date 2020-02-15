package com.infernal93.phonebookappmvvmanddagger.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.EditContactListener
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 10.02.2020.
 */
class EditContactViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val roomRepository: RoomRepository) : ViewModel() {

    var mFirstName: String = ""
    var mLastName: String = ""
    var mPhone: String = ""
    var mEmail: String = ""
    var mNotes: String = ""

    var mEditContactListener: EditContactListener? = null

    fun getOldData(name: String, lastName: String, phone: String, email: String, notes: String) {
        mFirstName = name
        mLastName = lastName
        mPhone = phone
        mEmail = email
        mNotes = notes
    }

    fun updateRoom(contactsRoom: ContactsRoom) {
        roomRepository.update(contactsRoom)
    }

    fun updateImageAndContact(id: String, toPath: String) {

        if (mFirstName.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_name)
        } else if (mLastName.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_last_name)
        } else if (mPhone.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_phone)
        } else if (mPhone.length != 12) {
            mEditContactListener?.showError(textResource = R.string.phone_invalid)
        } else if (mEmail.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_email)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEditContactListener?.showError(textResource = R.string.email_invalid)
        } else if (mNotes.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_notes)
        } else {

            val updateContactsRoom = ContactsRoom(
                _id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = "")

            updateRoom(updateContactsRoom)

            apiRepository.updateNewImageAndContact(
                id = id, name = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = toPath)
        }
    }

    fun updateContact(id: String) {

        if (mFirstName.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_name)
        } else if (mLastName.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_last_name)
        } else if (mPhone.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_phone)
        } else if (mPhone.length != 12) {
            mEditContactListener?.showError(textResource = R.string.phone_invalid)
        } else if (mEmail.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_email)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEditContactListener?.showError(textResource = R.string.email_invalid)
        } else if (mNotes.trim().isEmpty()) {
            mEditContactListener?.showError(textResource = R.string.empty_notes)
        } else {

            val updateContactsRoom = ContactsRoom(
                _id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = "")

            updateRoom(updateContactsRoom)

            apiRepository.updateNewContact(
                id = id, name = mFirstName, lastName = mLastName,
                phone = mPhone, email = mEmail, notes = mNotes)
        }
    }
}
