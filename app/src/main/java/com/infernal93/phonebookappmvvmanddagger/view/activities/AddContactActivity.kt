package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.model.ContactsModel
import com.infernal93.phonebookappmvvmanddagger.utils.shortToast
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactsViewModel
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add_contact.*
import javax.inject.Inject

class AddContactActivity : AppCompatActivity() {
    private val TAG = "AddContactActivity"


    companion object {
        const val EXTRA_FIRST_NAME = "com.infernal93.EXTRA_FIRST_NAME"
        const val EXTRA_LAST_NAME = "com.infernal93.EXTRA_LAST_NAME"
        const val EXTRA_PHONE = "com.infernal93.EXTRA_PHONE"
        const val EXTRA_EMAIL = "com.infernal93.EXTRA_EMAIL"
        const val EXTRA_NOTES = "com.infernal93.EXTRA_NOTES"
        const val EXTRA_IMAGE = 1
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var contactsViewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        // Toolbar implementation
        setSupportActionBar(toolbar_add_contact)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_cancel_save_btn)
        toolbar_add_contact.title = getString(R.string.add_contact_title)

        add_contact_image.setOnClickListener {
            getGalleryImage()
        }

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

        val firstName: String = add_contact_firstName.text.toString()
        val lastName: String = add_contact_lastName.text.toString()
        val phone: String = add_contact_phone.text.toString()
        val email: String = add_contact_email.text.toString()
        val notes: String = add_contact_notes.text.toString()

        if (firstName.trim().isEmpty()) {
            shortToast(getString(R.string.empty_name))
        }
        else if (lastName.trim().isEmpty()) {
            shortToast(getString(R.string.empty_last_name))
        }
        else if (phone.trim().isEmpty()) {
            shortToast(getString(R.string.empty_phone))
        }
        else if (email.trim().isEmpty()) {
            shortToast(getString(R.string.empty_email))
        }
        else if (notes.trim().isEmpty()) {
            shortToast(getString(R.string.empty_notes))
        } else {
//            val data = Intent()
//            data.putExtra(EXTRA_FIRST_NAME, firstName)
//            data.putExtra(EXTRA_LAST_NAME, lastName)
//            data.putExtra(EXTRA_PHONE, phone)
//            data.putExtra(EXTRA_EMAIL, email)
//            data.putExtra(EXTRA_NOTES, notes)
//
//            setResult(RESULT_OK, data)
//            finish()

            val contacts = ContactsModel(
                firstName = firstName, lastName = lastName, phone = phone,
                email = email, notes = notes, images = "")

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
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
