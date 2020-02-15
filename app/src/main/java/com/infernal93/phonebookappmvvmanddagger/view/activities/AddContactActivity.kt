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
    private var mImageForDB: String? = null
    private  var mPath: String = ""

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory
    lateinit var mAddContactListViewModel: AddContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAddContactBinding = DataBindingUtil.setContentView(this@AddContactActivity, R.layout.activity_add_contact)

        setSupportActionBar(mAddContactBinding.toolbarAddContact)
        mAddContactBinding.toolbarAddContact.title = getString(R.string.add_contact_title)


       add_contact_image.setOnClickListener {
           getImage()
       }

        mAddContactListViewModel = ViewModelProviders.of(this@AddContactActivity, mFactory)
            .get(AddContactViewModel::class.java)

        mAddContactBinding.addContactViewModel = mAddContactListViewModel
        mAddContactListViewModel.mAddContactListener = this
    }

    private fun getImage() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .start(this@AddContactActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val mResult = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {
                val mImageUri = mResult.uri

                mImageForDB = mImageUri.toString()

                if (mImageForDB == null) {
                    mImageForDB = R.drawable.ic_person_placeholder.toString()
                }  else {
                    mAddContactListViewModel.uploadImageForDb(mImageForDB!!)
                }

                mPath = mImageUri.path!!

                add_contact_image.setImageURI(mImageUri)
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
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

                when {
                    add_contact_firstName.text.trim().isEmpty() -> {
                        longToast(R.string.empty_name)
                    }
                    add_contact_lastName.text.trim().isEmpty() -> {
                        longToast(R.string.empty_last_name)
                    }
                    add_contact_phone.text.trim().isEmpty() -> {
                        longToast(R.string.empty_phone)
                    }
                    add_contact_phone.text.length != 12 -> {
                        longToast(R.string.phone_invalid)
                    }
                    add_contact_email.text.trim().isEmpty() -> {
                        longToast(R.string.empty_email)
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(add_contact_email.text).matches() -> {
                        longToast(R.string.email_invalid)
                    }
                    mPath.isEmpty() -> {
                        mAddContactListViewModel.saveContact()
                        finish()
                    }
                    else -> {
                        mAddContactListViewModel.saveImageAndContact(mPath)
                        finish()
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
