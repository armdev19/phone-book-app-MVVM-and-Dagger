package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.infernal93.phonebookappmvvmanddagger.view.adapters.ContactsAdapter
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.ContactListMainBinding
import com.infernal93.phonebookappmvvmanddagger.App
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactsViewModel
import javax.inject.Inject

class ContactListActivity : AppCompatActivity() {
    private val TAG = "ContactListActivity"

    lateinit var mContactListBinding: ContactListMainBinding
    private lateinit var mAdapter: ContactsAdapter

    companion object {
        const val ADD_CONTACT_REQUEST = 200
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var contactsViewModel: ContactsViewModel


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: ")


        mContactListBinding = DataBindingUtil.setContentView(this@ContactListActivity, R.layout.contact_list_main)

        setSupportActionBar(mContactListBinding.toolbarContacts)
        mContactListBinding.toolbarContacts.title = getString(R.string.contacts_toolbar_title)

        mContactListBinding.addNewContactBtn.setOnClickListener {
            val intent = Intent(this@ContactListActivity, AddContactActivity::class.java)
            startActivity(intent)
        }

        (applicationContext as App).appComponent.inject(this@ContactListActivity)



        contactsViewModel = ViewModelProviders.of(this@ContactListActivity, factory).get(ContactsViewModel::class.java)

        contactsViewModel.deleteAll()

        contactsViewModel.insertAll()



        mContactListBinding.recyclerContacts.layoutManager =
            LinearLayoutManager(applicationContext, OrientationHelper.VERTICAL, false)
        mContactListBinding.recyclerContacts.setHasFixedSize(true)

        mAdapter = ContactsAdapter(this@ContactListActivity)

        mContactListBinding.recyclerContacts.adapter = mAdapter

        contactsViewModel.getAllContacts().observe(this@ContactListActivity, Observer {

            // It works
           mAdapter.setupContacts(it as ArrayList<ContactsRoom>)

            // It works
            mAdapter.sortByName()



        })

    }



    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
//            val firstName: String? = data?.getStringExtra(AddContactActivity.EXTRA_FIRST_NAME)
//            val lastName: String? = data?.getStringExtra(AddContactActivity.EXTRA_LAST_NAME)
//            val phone: String? = data?.getStringExtra(AddContactActivity.EXTRA_PHONE)
//            val email: String? = data?.getStringExtra(AddContactActivity.EXTRA_EMAIL)
//            val notes: String? = data?.getStringExtra(AddContactActivity.EXTRA_NOTES)
//
//            val contacts = ContactsApi(firstName = firstName.toString(), lastName = lastName.toString(), phone = phone.toString(),
//                email = email.toString(), notes = notes.toString(), images = "")
//
//            //contactsViewModel.insert(contacts)
//            mAdapter.addItem(contacts)
//            mAdapter.notifyDataSetChanged()
//
//            mAdapter.clear()
//
//
//        }
//    }


}
