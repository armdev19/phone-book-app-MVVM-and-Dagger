package com.infernal93.phonebookappmvvmanddagger.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.UpdateDataListener
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 10.02.2020.
 */
class UpdateDataViewModel @Inject constructor(private val apiRepository: ApiRepository, private val roomRepository: RoomRepository) : ViewModel() {

    var mFirstName: String = ""
    var mLastName: String = ""
    var mPhone: String = ""
    var mEmail: String = ""
    var mNotes: String = ""

    var mUpdateDataListener: UpdateDataListener? = null

    fun uploadImageForDb(imageForDb: String) {
        apiRepository.NewImageDb(imageForDb)
    }

    fun update(contactsRoom: ContactsRoom) {
        roomRepository.update(contactsRoom)
    }

    fun updateContact(id: String) {

        if (mFirstName.trim().isEmpty()) {
            mUpdateDataListener?.showError(textResource = R.string.empty_name)
        } else if (mLastName.trim().isEmpty()) {
            mUpdateDataListener?.showError(textResource = R.string.empty_last_name)
        } else if (mPhone.trim().isEmpty()) {
            mUpdateDataListener?.showError(textResource = R.string.empty_phone)
        } else if (mPhone.length < 12) {
            mUpdateDataListener?.showError(textResource = R.string.phone_invalid)
        } else if (mEmail.trim().isEmpty()) {
            mUpdateDataListener?.showError(textResource = R.string.empty_email)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mUpdateDataListener?.showError(textResource = R.string.email_invalid)
        } else if (mNotes.trim().isEmpty()) {
            mUpdateDataListener?.showError(textResource = R.string.empty_notes)
        } else {

            val updateContactsRoom = ContactsRoom(
                _id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = apiRepository.newImageDB
            )


            apiRepository.updateContact(id = id, name = mFirstName, lastName = mLastName, phone = mPhone, email = mEmail,
                notes = mNotes, images = apiRepository.newImageDB)



            update(updateContactsRoom)
        }

    }

}
