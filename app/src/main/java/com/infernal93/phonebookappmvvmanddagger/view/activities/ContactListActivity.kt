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
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.ContactListMainBinding
import com.infernal93.phonebookappmvvmanddagger.model.ContactsModel
import com.infernal93.phonebookappmvvmanddagger.utils.App
import com.infernal93.phonebookappmvvmanddagger.view.adapters.ContactsAdapter
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactsViewModel
import javax.inject.Inject

class ContactListActivity : AppCompatActivity() {
    private val TAG = "ContactListActivity"

    lateinit var mBinding: ContactListMainBinding
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

        mBinding = DataBindingUtil.setContentView(this@ContactListActivity, R.layout.contact_list_main)

        setSupportActionBar(mBinding.toolbarContacts)
        mBinding.toolbarContacts.title = getString(R.string.contacts_toolbar_title)



        mBinding.addNewContactBtn.setOnClickListener {
            val intent = Intent(this@ContactListActivity, AddContactActivity::class.java)
            startActivity(intent)
        }

        (applicationContext as App).appComponent.inject(this@ContactListActivity)

        contactsViewModel = ViewModelProviders.of(this@ContactListActivity, factory).get(ContactsViewModel::class.java)


        contactsViewModel.getContactMutableLiveData().observe(this@ContactListActivity, Observer {

            mAdapter = ContactsAdapter(this@ContactListActivity, it as ArrayList<ContactsModel>)
            mBinding.recyclerContacts.layoutManager =
                LinearLayoutManager(applicationContext, OrientationHelper.VERTICAL, false)
            mBinding.recyclerContacts.adapter = mAdapter
            mBinding.recyclerContacts.setHasFixedSize(true)

            mAdapter.sortByName()

        })

    }
}
