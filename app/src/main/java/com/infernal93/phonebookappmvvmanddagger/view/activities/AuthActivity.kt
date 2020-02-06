package com.infernal93.phonebookappmvvmanddagger.view.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.ActivityAuthBinding
import com.infernal93.phonebookappmvvmanddagger.view.fragments.LoginFragment
import com.infernal93.phonebookappmvvmanddagger.viewmodels.AuthViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {
    private val TAG = "AuthActivity"

    private lateinit var mAuthBinding: ActivityAuthBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var mAuthViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuthBinding = DataBindingUtil.setContentView(this@AuthActivity, R.layout.activity_auth)

        mAuthViewModel = ViewModelProviders.of(this@AuthActivity, factory).get(AuthViewModel::class.java)

    }

}