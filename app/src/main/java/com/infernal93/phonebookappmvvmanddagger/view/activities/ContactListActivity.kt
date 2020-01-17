package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private lateinit var mContactListBinding: ContactListMainBinding
    private lateinit var mAdapter: ContactsAdapter

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var contactsViewModel: ContactsViewModel

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

           mAdapter.setupContacts(it as ArrayList<ContactsRoom>)

           mAdapter.sortByName()
        })
    }
}
