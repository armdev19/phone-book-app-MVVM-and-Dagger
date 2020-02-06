package com.infernal93.phonebookappmvvmanddagger.viewmodels

import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.entity.ImageResponse
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.AddContactListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 02.02.2020.
 */
class AddContactViewModel @Inject constructor(private val roomRepository: RoomRepository, private val contactsService: ContactsService): ViewModel() {

    var mFirstName: String = ""
    var mLastName: String = ""
    var mPhone: String = ""
    var mEmail: String = ""
    var mNotes: String = ""

    var mAddContactListener: AddContactListener? = null

    fun getImage(view: View) {
        mAddContactListener?.getGalleryImage()
    }

    fun insert(contactsRoom: ContactsRoom){
        roomRepository.insert(contactsRoom)
    }

    fun uploadImage(){

    }

    fun saveContact(view: View) {
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
            val newContactsRoom = ContactsRoom(firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = mAddContactListener?.getGalleryImage().toString())

            insert(newContactsRoom)

            val newContactsApi = ContactsApi(firstName = mFirstName, lastName = mLastName, phone = mPhone,
                email = mEmail, notes = mNotes, images = mAddContactListener?.getGalleryImage().toString())

                contactsService.postNewContact(newContactsApi).enqueue(object : Callback<ContactsApi>{
                    override fun onFailure(call: Call<ContactsApi>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<ContactsApi>, response: Response<ContactsApi>) {

                    }

                })
        }

    }


}