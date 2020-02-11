package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    private val TAG = "DetailsActivity"

    private lateinit  var toolbar: Toolbar
    private lateinit var mDetailsBinding: ActivityDetailsBinding

    lateinit var  clickedContactsRoom: ContactsRoom

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mDetailsBinding = DataBindingUtil.setContentView(this@DetailsActivity, R.layout.activity_details)

        detailsViewModel = ViewModelProviders.of(this@DetailsActivity, factory).get(DetailsViewModel::class.java)

        mDetailsBinding.detailsViewModel = detailsViewModel

        // Toolbar implementation
        toolbar = findViewById(R.id.toolbar_details_info)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.title = getString(R.string.details_toolbar_title)

        toolbar.setNavigationOnClickListener{
            onBackPressed()
            overridePendingTransition(0, 0)
        }

        clickedContactsRoom = intent.getSerializableExtra("contact")
                as ContactsRoom

        getDetailInfoContact()

        edit_button.setOnClickListener {

            val intent = Intent(this@DetailsActivity, UpdateDataActivity::class.java)
            intent.putExtra("updateData", clickedContactsRoom)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)

           // startActivity(Intent(this@DetailsActivity, UpdateDataActivity::class.java))

        }
    }

    private fun getDetailInfoContact() {


        details_first_name.text = clickedContactsRoom.firstName
        details_last_name.text = clickedContactsRoom.lastName
        details_phone.text = clickedContactsRoom.phone
        details_email.text = clickedContactsRoom.email
        details_notes.text = clickedContactsRoom.notes

        delete_button.setOnClickListener {

            val id: String? = clickedContactsRoom.images?.substring(42)
            Toast.makeText(this, "OK $id", Toast.LENGTH_SHORT).show()

            detailsViewModel.delete(clickedContactsRoom)
            detailsViewModel.deleteImage(id)
            detailsViewModel.deleteContact(clickedContactsRoom._id)
            finish()
        }

        if (clickedContactsRoom.images.isNullOrEmpty()) {
            details_image.setImageResource(R.drawable.ic_person_placeholder)
        } else {
            Picasso.with(this@DetailsActivity).load(clickedContactsRoom.images)
            .placeholder(R.drawable.ic_person_placeholder)
            .into(details_image)
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}
