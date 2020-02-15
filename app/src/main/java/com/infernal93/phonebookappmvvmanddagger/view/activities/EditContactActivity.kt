package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.ActivityEditContactBinding
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.utils.longToast
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.EditContactListener
import com.infernal93.phonebookappmvvmanddagger.viewmodels.EditContactViewModel
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_contact.*
import javax.inject.Inject

class EditContactActivity : DaggerAppCompatActivity(), EditContactListener {

    private lateinit var mEditContactBinding: ActivityEditContactBinding
    lateinit var  mOldDataContactsRoom: ContactsRoom
    private var mImageForDB: String = ""
    private  var mPath: String = ""

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory
    lateinit var mUpdateViewModel: EditContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mEditContactBinding = DataBindingUtil.setContentView(this@EditContactActivity, R.layout.activity_edit_contact)

        mUpdateViewModel = ViewModelProviders.of(this@EditContactActivity, mFactory).get(EditContactViewModel::class.java)

        mEditContactBinding.updateDataVieModel = mUpdateViewModel
        mUpdateViewModel.mEditContactListener = this


        save_newData_btn.setOnClickListener {
            updateContact()
        }

        new_image.setOnClickListener {
            getImage()
        }

        mOldDataContactsRoom = intent.getSerializableExtra("updateData")
                as ContactsRoom

        showOldData()
    }

    private fun getImage() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .start(this@EditContactActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val mResult = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {
                val mImageUri = mResult.uri

                mImageForDB = mImageUri.toString()
                mPath = mResult.uri.path.toString()
                mEditContactBinding.newImage.setImageURI(mImageUri)
            }
        }
    }

    private fun showOldData(){
        new_firstName.hint = mOldDataContactsRoom.firstName
        new_lastName.hint =mOldDataContactsRoom.lastName
        new_phone.hint = mOldDataContactsRoom.phone
        new_email.hint = mOldDataContactsRoom.email
        new_notes.hint = mOldDataContactsRoom.notes

        mUpdateViewModel.getOldData(name = mOldDataContactsRoom.firstName,
            lastName = mOldDataContactsRoom.lastName,
            phone = mOldDataContactsRoom.phone,
            email = mOldDataContactsRoom.email,
            notes = mOldDataContactsRoom.notes)

        if (mOldDataContactsRoom.images.isEmpty()) {
            new_image.setImageResource(R.drawable.ic_person_placeholder)
        } else {
            Picasso.with(this@EditContactActivity).load(mOldDataContactsRoom.images)
                .placeholder(R.drawable.ic_person_placeholder)
                .into(new_image)
        }
    }

    override fun showError(textResource: Int) {
        Toast.makeText(this@EditContactActivity, getString(textResource), Toast.LENGTH_LONG).show()
    }

    private fun updateContact(){
        if(new_firstName.text.toString() == mOldDataContactsRoom.firstName &&
                new_lastName.text.toString() == mOldDataContactsRoom.lastName &&
                new_phone.text.toString() == mOldDataContactsRoom.phone &&
                new_email.text.toString() == mOldDataContactsRoom.email &&
                new_notes.text.toString() == mOldDataContactsRoom.notes &&
                mPath.isEmpty()) {
            longToast(R.string.data_to_update_empty)
        } else {

            if (mEditContactBinding.newFirstName.text.isNullOrEmpty()) {
                mEditContactBinding.newFirstName.setText(mOldDataContactsRoom.firstName)
            } else if (mEditContactBinding.newLastName.text.isNullOrEmpty()) {
                mEditContactBinding.newLastName.setText(mOldDataContactsRoom.lastName)
            } else if (mEditContactBinding.newPhone.text.isNullOrEmpty()) {
                mEditContactBinding.newPhone.setText(mOldDataContactsRoom.phone)
            } else if (mEditContactBinding.newEmail.text.isNullOrEmpty()) {
                mEditContactBinding.newEmail.setText(mOldDataContactsRoom.email)
            } else if (mEditContactBinding.newNotes.text.isNullOrEmpty()) {
                mEditContactBinding.newNotes.setText(mOldDataContactsRoom.notes)
            } else  {

                mOldDataContactsRoom.firstName = new_firstName.text.toString()
                mOldDataContactsRoom.lastName = new_lastName.text.toString()
                mOldDataContactsRoom.phone = new_phone.text.toString()
                mOldDataContactsRoom.email = new_email.text.toString()
                mOldDataContactsRoom.notes = new_notes.text.toString()

                if (mImageForDB.isEmpty() && mPath.isEmpty()) {

                    mUpdateViewModel.updateRoom(mOldDataContactsRoom)
                    mUpdateViewModel.updateContact(mOldDataContactsRoom._id)
                    onBackPressed()
                } else {
                    mOldDataContactsRoom.images = mImageForDB
                    mUpdateViewModel.updateRoom(mOldDataContactsRoom)
                    mUpdateViewModel.updateImageAndContact(mOldDataContactsRoom._id, mPath)
                    onBackPressed()
                }
            }
        }
    }
}
