package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.ActivityDetailsBinding
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.viewmodels.DetailsViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : DaggerAppCompatActivity() {

    private lateinit  var mToolbar: Toolbar
    private lateinit var mDetailsBinding: ActivityDetailsBinding
    lateinit var  mClickedContactsRoom: ContactsRoom

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory
    lateinit var mDetailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDetailsBinding = DataBindingUtil.setContentView(this@DetailsActivity, R.layout.activity_details)

        mDetailsViewModel = ViewModelProviders.of(this@DetailsActivity, mFactory).get(DetailsViewModel::class.java)

        mDetailsBinding.detailsViewModel = mDetailsViewModel

        mToolbar = findViewById(R.id.toolbar_details_info)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mToolbar.title = getString(R.string.details_toolbar_title)

        mToolbar.setNavigationOnClickListener{
            onBackPressed()
            overridePendingTransition(0, 0)
        }

        mClickedContactsRoom = intent.getSerializableExtra("contact")
                as ContactsRoom

        edit_button.setOnClickListener {
            sendToEditContactActivity()
        }

        delete_button.setOnClickListener {
            deleteContact()
        }

        getDetailInfoContact()
    }

    private fun getDetailInfoContact() {
        details_first_name.text = mClickedContactsRoom.firstName
        details_last_name.text = mClickedContactsRoom.lastName
        details_phone.text = mClickedContactsRoom.phone
        details_email.text = mClickedContactsRoom.email
        details_notes.text = mClickedContactsRoom.notes

        if (mClickedContactsRoom.images.isEmpty()) {
            details_image.setImageResource(R.drawable.ic_person_placeholder)
        } else {
            Picasso.with(this@DetailsActivity).load(mClickedContactsRoom.images)
            .placeholder(R.drawable.ic_person_placeholder)
            .into(details_image)
        }
    }

    private fun deleteContact(){
        var mId: String? = ""

        if (mClickedContactsRoom.images.isNotEmpty()) {
            mId = mClickedContactsRoom.images.substringAfterLast(delimiter = "/")
        }

        if (mId.isNullOrEmpty()) {
            mDetailsViewModel.deleteRoom(mClickedContactsRoom)
            mDetailsViewModel.deleteContact(mClickedContactsRoom._id)
            finish()
        } else {
            mDetailsViewModel.deleteRoom(mClickedContactsRoom)
            mDetailsViewModel.deleteImage(mId)
            mDetailsViewModel.deleteContact(mClickedContactsRoom._id)
            finish()
        }
    }

    private fun sendToEditContactActivity(){
        val intent = Intent(this@DetailsActivity, EditContactActivity::class.java)
        intent.putExtra("updateData", mClickedContactsRoom)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}
