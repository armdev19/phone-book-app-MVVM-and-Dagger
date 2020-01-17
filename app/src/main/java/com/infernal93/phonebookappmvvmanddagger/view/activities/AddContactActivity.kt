package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.infernal93.phonebookappmvvmanddagger.App
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.databinding.ActivityAddContactBinding
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsApi
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.entity.ImageResponse
import com.infernal93.phonebookappmvvmanddagger.utils.shortToast
import com.infernal93.phonebookappmvvmanddagger.view.adapters.ContactsAdapter
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactsViewModel
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add_contact.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject


class AddContactActivity : AppCompatActivity() {
    private val TAG = "AddContactActivity"

    lateinit var mAddContactBinding: ActivityAddContactBinding
    private lateinit var mAdapter: ContactsAdapter
    var imageMediaId: String = ""

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var addContactsViewModel: ContactsViewModel

    private lateinit var contactsService: ContactsService
    private lateinit var imagesService: ImagesService

    private var imgUri: Uri? = null
    private var imageForDB: String? = null
    private var  bitmap: Bitmap? = null
    private var toPath: String? = null

    companion object { const val EXTRA_IMAGE = 1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        mAddContactBinding =
            DataBindingUtil.setContentView(this@AddContactActivity, R.layout.activity_add_contact)

        setSupportActionBar(toolbar_add_contact)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_cancel_save_btn)
        toolbar_add_contact.title = getString(R.string.add_contact_title)

        add_contact_image.setOnClickListener {
            getGalleryImage()
        }

        (applicationContext as App).appComponent.inject(this@AddContactActivity)

        contactsService = (applicationContext as App).appComponent.contactsService()
        imagesService = (applicationContext as App).appComponent.imagesService()

        addContactsViewModel = ViewModelProviders.of(this@AddContactActivity, factory)
            .get(ContactsViewModel::class.java)

        mAdapter = ContactsAdapter(this@AddContactActivity)

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
                imgUri = resultUri
                imageForDB = resultUri.toString()
                bitmap = result.bitmap
                val toPath : String = result.uri.path!!

                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imgUri)


                val file = File(toPath)

                val fileReqBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)

                val part: MultipartBody.Part = MultipartBody.Part.
                    createFormData("upload", file.name, fileReqBody)


                imagesService.postImage(part).enqueue(object : Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.code() == 201) {

                            val gson = Gson()




                            val testModel = gson.fromJson(response.body()?.string(), ImageResponse::class.java)

                            imageMediaId  = testModel.ids[0]
                            Log.d(TAG, "onResponse: $imageMediaId")

                            //Log.d(TAG, "onResponse: ${response.body()?.string()}")
//                            Log.d(TAG, "onResponse: ${response.message().toString()}")
//                            Log.d(TAG, "onResponse: ${response.body()}")
//                            Log.d(TAG, "onResponse: ${response.body().toString()}")
                            //Log.d(TAG, "onResponse: ${testModel.uploadid}")


                        }
                    }

                })


                add_contact_image.setImageURI(resultUri)
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }


    private fun getGalleryImage() {
        val galleryIntent = Intent()
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, EXTRA_IMAGE)
    }


    private fun saveContact() {




        //if (imgUri == null) imgUri = R.drawable.ic_person_placeholder


        val firstName: String = add_contact_firstName.text.toString()
        val lastName: String = add_contact_lastName.text.toString()
        val phone: String = add_contact_phone.text.toString()
        val email: String = add_contact_email.text.toString()
        val notes: String = add_contact_notes.text.toString()

        if (firstName.trim().isEmpty()) {
            shortToast(getString(R.string.empty_name))
        } else if (lastName.trim().isEmpty()) {
            shortToast(getString(R.string.empty_last_name))
        } else if (phone.trim().isEmpty()) {
            shortToast(getString(R.string.empty_phone))
        } else if (email.trim().isEmpty()) {
            shortToast(getString(R.string.empty_email))
        } else if (notes.trim().isEmpty()) {
            shortToast(getString(R.string.empty_notes))
        } else {
            val newContactRoom = ContactsRoom(
                    firstName = firstName, lastName = lastName, phone = phone,
                    email = email, notes = notes, images = imageForDB)

            val newContactApi = ContactsApi(
                    firstName = firstName, lastName = lastName, phone = phone,
                    email = email, notes = notes, images = "https://phonebookapp-683c.restdb.io/media/$imageMediaId")


            contactsService.postNewContact(newContactApi).enqueue(object : Callback<ContactsApi> {
                override fun onFailure(call: Call<ContactsApi>, t: Throwable) {

                }

                override fun onResponse(call: Call<ContactsApi>, response: Response<ContactsApi>) {
                }

            })

            addContactsViewModel.insert(newContactRoom)

            finish()
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
