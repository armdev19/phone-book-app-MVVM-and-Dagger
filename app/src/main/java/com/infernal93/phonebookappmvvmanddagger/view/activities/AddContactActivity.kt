package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.databinding.ActivityAddContactBinding
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.entity.ImageResponse
import com.infernal93.phonebookappmvvmanddagger.utils.shortToast
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.AddContactListener
import com.infernal93.phonebookappmvvmanddagger.viewmodels.AddContactViewModel
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactListViewModel
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_add_contact.*
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


class AddContactActivity : DaggerAppCompatActivity(), AddContactListener {
    private val TAG = "AddContactActivity"

    private lateinit var mAddContactBinding: ActivityAddContactBinding
    var imageMediaId: String = ""
    private var imageForDB: String? = null

    private lateinit var contactsService: ContactsService
    private lateinit var imagesService: ImagesService

    companion object { const val EXTRA_IMAGE = 1 }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var addContactListViewModel: AddContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mAddContactBinding = DataBindingUtil.setContentView(this@AddContactActivity, R.layout.activity_add_contact)

        setSupportActionBar(mAddContactBinding.toolbarAddContact)
        mAddContactBinding.toolbarAddContact.title = getString(R.string.add_contact_title)

//        mAddContactBinding.addContactImage.setOnClickListener {
//            getGalleryImage()
//        }



        //contactsService = (applicationContext as App).appComponent.contactsService()
       // imagesService = (applicationContext as App).appComponent.imagesService()

        addContactListViewModel = ViewModelProviders.of(this@AddContactActivity, factory)
            .get(AddContactViewModel::class.java)

        addContactListViewModel.mAddContactListener = this

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EXTRA_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data

            CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .start(this@AddContactActivity)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {

                val resultUri = result.uri
                imageForDB = resultUri.toString()


                val toPath: String = result.uri.path!!


                val file = File(toPath)
                val fileReqBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val part: MultipartBody.Part = MultipartBody.Part.
                    createFormData("upload", file.name, fileReqBody)

                imagesService.postImage(part).enqueue(object : Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.code() == 201) {

                            // Get Image response Id
                            val gson = Gson()
                            val imageResponse = gson.fromJson(response.body()?.string(), ImageResponse::class.java)
                            imageMediaId  = imageResponse.ids[0]
                        }
                    }
                })

                mAddContactBinding.addContactImage.setImageURI(resultUri)
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun getGalleryImage() {
        val galleryIntent = Intent()
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, EXTRA_IMAGE)
    }

    override fun showError(textResource: Int) {
        Toast.makeText(this@AddContactActivity, getString(textResource), Toast.LENGTH_LONG).show()
    }

    override fun savePlaceholder() {
        if (imageForDB == null) imageForDB = R.drawable.ic_person_placeholder.toString()
    }

    private fun saveContact() {

        val firstName: String = add_contact_firstName.text.toString()
        val lastName: String = add_contact_lastName.text.toString()
        val phone: String = add_contact_phone.text.toString()
        val email: String = add_contact_email.text.toString()
        val notes: String = add_contact_notes.text.toString()

        when {
            firstName.trim().isEmpty() -> {
                shortToast(R.string.empty_name)
            }
            lastName.trim().isEmpty() -> {
                shortToast(R.string.empty_last_name)
            }
            phone.trim().isEmpty() -> {
                shortToast(R.string.empty_phone)
            }
            email.trim().isEmpty() -> {
                shortToast(R.string.empty_email)
            }
            notes.trim().isEmpty() -> {
                shortToast(R.string.empty_notes)
            }
            else -> {
                val newContactRoom = ContactsRoom(
                    firstName = firstName, lastName = lastName, phone = phone,
                    email = email, notes = notes, images = imageForDB)

                addContactListViewModel.insert(newContactRoom)

                val newContactApi = ContactsApi(
                    firstName = firstName, lastName = lastName, phone = phone,
                    email = email, notes = notes, images = "https://phonebookapp-683c.restdb.io/media/$imageMediaId")

                contactsService.postNewContact(newContactApi).enqueue(object : Callback<ContactsApi> {
                    override fun onFailure(call: Call<ContactsApi>, t: Throwable) {}

                    override fun onResponse(call: Call<ContactsApi>, response: Response<ContactsApi>) {}
                })

                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.add_contact_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_contact -> {
                saveContact()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
