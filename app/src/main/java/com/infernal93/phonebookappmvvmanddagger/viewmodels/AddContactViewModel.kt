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
                                              private val contactsService: ContactsService,
                                              private val imagesService: ImagesService,
                                              private val apiRepository: ApiRepository): ViewModel() {

    var mFirstName: String = ""
    var mLastName: String = ""
    var mPhone: String = ""
    var mEmail: String = ""
    var mNotes: String = ""

    var mAddContactListener: AddContactListener? = null

//    fun getImage(view: View) {
//        mAddContactListener?.getGalleryImage()
//    }

    fun insert(contactsRoom: ContactsRoom){
        roomRepository.insert(contactsRoom)
    }



    fun uploadImage(toPath: String?){
        apiRepository.uploadNewContactImage(toPath)
    }

    fun uploadImageForDb(imageForDb: String) {
        apiRepository.ImageDb(imageForDb)
    }



    fun saveContact() {
        mAddContactListener?.savePlaceholder()

        if (mFirstName.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_name)
        } else if (mLastName.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_last_name)
        } else if (mPhone.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_phone)
        } else if (mEmail.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_email)
        } else if (mNotes.trim().isEmpty()) {
            mAddContactListener?.showError(textResource = R.string.empty_notes)
        } else if (!Patterns.PHONE.matcher(mPhone).matches()) {
            mAddContactListener?.showError(textResource = R.string.phone_invalid)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mAddContactListener?.showError(textResource = R.string.email_invalid)
        } else {
            val newContactsRoom = ContactsRoom(_id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = apiRepository.imageDB)

            insert(newContactsRoom)

            val newContactsApi = ContactsApi(id = "", firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = "https://phonebookapp-683c.restdb.io/media/${apiRepository.imageMediaId}")


            apiRepository.uploadNewContact(newContactsApi)

//                contactsService.postNewContact(newContactsApi).enqueue(object : Callback<ContactsApi>{
//                    override fun onFailure(call: Call<ContactsApi>, t: Throwable) {
//
//                    }
//
//                    override fun onResponse(call: Call<ContactsApi>, response: Response<ContactsApi>) {
//
//                    }
//
//                })
        }

    }


}