package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
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
import kotlinx.android.synthetic.main.contact_list_main.*
import javax.inject.Inject

class ContactListActivity : AppCompatActivity() {
    private val TAG = "ContactListActivity"

    lateinit var activityMainBinding: ContactListMainBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var contactsViewModel: ContactsViewModel

    private lateinit  var toolbar: Toolbar
    private lateinit var addNewContactBtn: ImageButton

    private lateinit var mAdapter: ContactsAdapter


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_list_main)

        toolbar = findViewById(R.id.toolbar_contacts)
        addNewContactBtn = findViewById(R.id.add_new_contact_btn)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.contacts_toolbar_title)

        addNewContactBtn.setOnClickListener {
            val intent = Intent(this@ContactListActivity, AddContactActivity::class.java)
            startActivity(intent)
        }

        activityMainBinding = DataBindingUtil.setContentView(this@ContactListActivity, R.layout.contact_list_main)

        (applicationContext as App).appComponent.inject(this@ContactListActivity)

        contactsViewModel = ViewModelProviders.of(this@ContactListActivity, factory).get(ContactsViewModel::class.java)


        contactsViewModel.getContactMutableLiveData().observe(this@ContactListActivity, Observer {

            mAdapter = ContactsAdapter(this@ContactListActivity, it as ArrayList<ContactsModel>)
            recycler_contacts.layoutManager =
                LinearLayoutManager(applicationContext, OrientationHelper.VERTICAL, false)
            recycler_contacts.adapter = mAdapter
            recycler_contacts.setHasFixedSize(true)

            mAdapter.sortByName()

        })

    }
}
