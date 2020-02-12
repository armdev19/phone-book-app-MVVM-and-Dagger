package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.ActivityAddContactBinding
import com.infernal93.phonebookappmvvmanddagger.utils.longToast
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.AddContactListener
import com.infernal93.phonebookappmvvmanddagger.viewmodels.AddContactViewModel
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_add_contact.*
import javax.inject.Inject

class AddContactActivity : DaggerAppCompatActivity(), AddContactListener {

    private lateinit var mAddContactBinding: ActivityAddContactBinding
    private var imageForDB: String? = null
    private  var toPath: String? = null

    companion object { const val EXTRA_IMAGE = 1 }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var addContactListViewModel: AddContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAddContactBinding = DataBindingUtil.setContentView(this@AddContactActivity, R.layout.activity_add_contact)

        setSupportActionBar(mAddContactBinding.toolbarAddContact)
        mAddContactBinding.toolbarAddContact.title = getString(R.string.add_contact_title)

        mAddContactBinding.addContactImage.setOnClickListener {
            getGalleryImage()
        }

        add_contact_image.setOnClickListener {
            getGalleryImage()
        }

        addContactListViewModel = ViewModelProviders.of(this@AddContactActivity, factory)
            .get(AddContactViewModel::class.java)

        mAddContactBinding.addContactViewModel = addContactListViewModel
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

                if (imageForDB == null) {
                    imageForDB = R.drawable.ic_person_placeholder.toString()
                } else {
                    addContactListViewModel.uploadImageForDb(imageForDB!!)
                }

                 toPath = result.uri.path

                mAddContactBinding.addContactImage.setImageURI(resultUri)
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

    override fun showError(textResource: Int) {
        Toast.makeText(this@AddContactActivity, getString(textResource), Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.add_contact_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_contact -> {

                if (add_contact_firstName.text.trim().isEmpty()) {
                    longToast(R.string.empty_name)
                } else if (add_contact_lastName.text.trim().isEmpty()) {
                    longToast(R.string.empty_last_name)
                } else if (add_contact_phone.text.trim().isEmpty()) {
                    longToast(R.string.empty_phone)
                } else if (add_contact_phone.text.length < 12) {
                    longToast(R.string.phone_invalid)
                } else if (add_contact_email.text.trim().isEmpty()) {
                    longToast(R.string.empty_email)
                } else if (!Patterns.EMAIL_ADDRESS.matcher(add_contact_email.text).matches()) {
                    longToast(R.string.email_invalid)
                } else if (toPath == null) {
                    addContactListViewModel.saveContact()
                    finish()
                } else {
                    addContactListViewModel.saveImageAndContact(toPath)
                    finish()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
