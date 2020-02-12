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
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.EditContactListener
import com.infernal93.phonebookappmvvmanddagger.viewmodels.EditContactViewModel
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_contact.*
import javax.inject.Inject

class EditContactActivity : DaggerAppCompatActivity(), EditContactListener {

    private lateinit var mEditContactBinding: ActivityEditContactBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var mUpdateViewModel: EditContactViewModel

    lateinit var  oldDataContactsRoom: ContactsRoom

    companion object { const val EXTRA_IMAGE = 1 }
    private var imageForDB: String? = null

    private  var toPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mEditContactBinding = DataBindingUtil.setContentView(this@EditContactActivity, R.layout.activity_edit_contact)

        mUpdateViewModel = ViewModelProviders.of(this@EditContactActivity, factory).get(EditContactViewModel::class.java)

        mEditContactBinding.updateDataVieModel = mUpdateViewModel
        mUpdateViewModel.mEditContactListener = this


        save_newData_btn.setOnClickListener {
            update()
            sendToContactListActivity()
        }

        new_image.setOnClickListener {
            getGalleryImage()
        }

        oldDataContactsRoom = intent.getSerializableExtra("updateData")
                as ContactsRoom

        showOldData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EXTRA_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data

            CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .start(this@EditContactActivity)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {

                val resultUri = result.uri
                imageForDB = resultUri.toString()

                if (imageForDB == null) {
                    imageForDB = R.drawable.ic_person_placeholder.toString()
                } else {
                    mUpdateViewModel.uploadImageForDb(imageForDB!!)
                }

                toPath = result.uri.path

                mEditContactBinding.newImage.setImageURI(resultUri)
            }
        }
    }

    private fun getGalleryImage() {
        val galleryIntent = Intent()
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, EXTRA_IMAGE)
    }

    private fun showOldData(){
        new_firstName.hint = oldDataContactsRoom.firstName
        new_lastName.hint =oldDataContactsRoom.lastName
        new_phone.hint = oldDataContactsRoom.phone
        new_email.hint = oldDataContactsRoom.email
        new_notes.hint = oldDataContactsRoom.notes

        if (oldDataContactsRoom.images.isNullOrEmpty()) {
            new_image.setImageResource(R.drawable.ic_person_placeholder)
        } else {
            Picasso.with(this@EditContactActivity).load(oldDataContactsRoom.images)
                .placeholder(R.drawable.ic_person_placeholder)
                .into(new_image)
        }
    }

    private fun sendToContactListActivity(){
        startActivity(Intent(this, ContactListActivity::class.java))
    }

    override fun showError(textResource: Int) {
        Toast.makeText(this@EditContactActivity, getString(textResource), Toast.LENGTH_LONG).show()
    }

    private fun update(){
        if (toPath == null) {
            toPath = R.drawable.ic_person_placeholder.toString()
        }

        val newContactsRoom = ContactsRoom(_id = "", firstName = new_firstName.text.toString(),
            lastName = new_lastName.text.toString(),
            phone = new_phone.text.toString(),
            email = new_email.text.toString(),
            notes = new_notes.text.toString(),
            images = imageForDB)

        mUpdateViewModel.update(newContactsRoom)

        mUpdateViewModel.updateContact(oldDataContactsRoom._id)
    }
}
