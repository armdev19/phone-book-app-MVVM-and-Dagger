package com.infernal93.phonebookappmvvmanddagger.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.databinding.FragmentLoginBinding
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.LoginListener
import com.infernal93.phonebookappmvvmanddagger.view.activities.ContactListActivity
import com.infernal93.phonebookappmvvmanddagger.viewmodels.AuthViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import javax.inject.Inject

class LoginFragment : DaggerFragment(), View.OnClickListener, TextWatcher, LoginListener {

    private lateinit var mNavController: NavController
    lateinit var mBinding: FragmentLoginBinding

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory
    lateinit var mAuthViewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)

        mAuthViewModel = ViewModelProviders.of(this@LoginFragment, mFactory).get(AuthViewModel::class.java)

        mBinding.loginViewModel = mAuthViewModel
        mAuthViewModel.mLoginListener = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        view.btn_registration.setOnClickListener(this)
        login_email.addTextChangedListener(this)
        login_password.addTextChangedListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_registration -> mNavController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun startLoading() {
        btn_login_enter.visibility = View.GONE
        btn_registration.visibility = View.GONE
        cpv_login.visibility = View.VISIBLE
    }

    override fun endLoading() {
        btn_login_enter.visibility = View.VISIBLE
        btn_registration.visibility = View.VISIBLE
        cpv_login.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(context, getString(textResource), Toast.LENGTH_LONG).show()
    }

    override fun showError(textResource: String) {
        Toast.makeText(context, textResource, Toast.LENGTH_LONG).show()
    }

    override fun validateLoginAndPassword() {
        val intent = Intent(activity, ContactListActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun afterTextChanged(p0: Editable?) {
        btn_login_enter.isEnabled =
            login_email.text.toString().isNotEmpty() &&
            login_password.text.toString().isNotEmpty()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}
