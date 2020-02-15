package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.google.firebase.auth.FirebaseAuth
import com.infernal93.phonebookappmvvmanddagger.view.adapters.ContactsAdapter
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.ActivityContactListBinding
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom
import com.infernal93.phonebookappmvvmanddagger.utils.longToast
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.ErrorListener
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactListViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ContactListActivity : DaggerAppCompatActivity(), ErrorListener {

    private lateinit var mContactListBinding: ActivityContactListBinding
    private lateinit var mAdapter: ContactsAdapter
    private lateinit var mAuth: FirebaseAuth

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory
    lateinit var mContactListViewModel: ContactListViewModel

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContactListBinding = DataBindingUtil.setContentView(this@ContactListActivity, R.layout.activity_contact_list)

        mAuth = FirebaseAuth.getInstance()

        setSupportActionBar(mContactListBinding.toolbarContacts)
        mContactListBinding.toolbarContacts.title = getString(R.string.contacts_toolbar_title)

        mContactListBinding.addNewContactBtn.setOnClickListener {
            val intent = Intent(this@ContactListActivity, AddContactActivity::class.java)
            startActivity(intent)
        }

        mContactListViewModel = ViewModelProviders.of(this@ContactListActivity, mFactory).get(ContactListViewModel::class.java)

        mContactListViewModel.deleteAllRoom()
        mContactListViewModel.insertAllRoom()

        mContactListBinding.recyclerContacts.layoutManager =
            LinearLayoutManager(applicationContext, OrientationHelper.VERTICAL, false)
        mContactListBinding.recyclerContacts.setHasFixedSize(true)

        mAdapter = ContactsAdapter(this@ContactListActivity)

        mContactListBinding.recyclerContacts.adapter = mAdapter

        mContactListViewModel.getAllContacts().observe(this@ContactListActivity, Observer {

           mAdapter.setupContacts(it as ArrayList<ContactsRoom>)

           mAdapter.sortByName()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contacts_list_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_log_out) {
            mContactListViewModel.logOut()
            startActivity(Intent(this@ContactListActivity, AuthActivity::class.java))
            finish()
        }
        return true
    }

    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser == null) {
            mContactListViewModel.logOut()
            startActivity(Intent(this@ContactListActivity, AuthActivity::class.java))
            finish()
        }
    }

    override fun showMessage(textResource: String) {
        Toast.makeText(this, textResource, Toast.LENGTH_LONG).show()
    }

    override fun showError(textResource: Int) {

    }
}
