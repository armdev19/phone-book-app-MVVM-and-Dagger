package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.ActivityUpdateDataBinding
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.UpdateDataListener
import com.infernal93.phonebookappmvvmanddagger.viewmodels.UpdateDataViewModel
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_update_data.*
import javax.inject.Inject

class UpdateDataActivity : DaggerAppCompatActivity(), UpdateDataListener {
    private val TAG = "UpdateDataActivity"

    private lateinit var mUpdateDataBinding: ActivityUpdateDataBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var mUpdateViewModel: UpdateDataViewModel

    lateinit var  oldDataContactsRoom: ContactsRoom

    companion object { const val EXTRA_IMAGE = 1 }
    private var imageForDB: String? = null

    private  var toPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mUpdateDataBinding = DataBindingUtil.setContentView(this@UpdateDataActivity, R.layout.activity_update_data)

        mUpdateViewModel = ViewModelProviders.of(this@UpdateDataActivity, factory).get(UpdateDataViewModel::class.java)

        mUpdateDataBinding.updateDataVieModel = mUpdateViewModel

        mUpdateViewModel.mUpdateDataListener = this


        save_newData_btn.setOnClickListener {
            update()
            back()
        }

        oldDataContactsRoom = intent.getSerializableExtra("updateData")
                as ContactsRoom

        showOldData()

        new_image.setOnClickListener {
            getGalleryImage()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EXTRA_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data

            CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .start(this@UpdateDataActivity)
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

                mUpdateDataBinding.newImage.setImageURI(resultUri)
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
            Picasso.with(this@UpdateDataActivity).load(oldDataContactsRoom.images)
                .placeholder(R.drawable.ic_person_placeholder)
                .into(new_image)
        }
    }

    private fun back(){
        //onBackPressed()
        startActivity(Intent(this, ContactListActivity::class.java))
        //finish()
    }

    override fun showError(textResource: Int) {
        Toast.makeText(this@UpdateDataActivity, getString(textResource), Toast.LENGTH_LONG).show()
    }

    fun update(){
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
