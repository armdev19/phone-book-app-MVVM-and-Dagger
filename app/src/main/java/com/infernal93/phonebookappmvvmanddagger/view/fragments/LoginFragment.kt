package com.infernal93.phonebookappmvvmanddagger.view.fragments

import android.content.Intent
import android.os.Bundle
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
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import javax.inject.Inject


class LoginFragment : DaggerFragment(), View.OnClickListener, KeyboardVisibilityEventListener,
    LoginListener {

    private lateinit var navController: NavController
    lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var mAuthViewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        mAuthViewModel = ViewModelProviders.of(this@LoginFragment, factory).get(AuthViewModel::class.java)

        binding.loginViewModel = mAuthViewModel
        mAuthViewModel.loginListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.btn_registration.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.btn_registration -> navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if (isKeyboardOpen) {
            root_scroll_view.scrollTo(0, root_scroll_view.bottom)
        } else {
            root_scroll_view.scrollTo(0, root_scroll_view.top)
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

    override fun validateLoginAndPassword() {
        val intent = Intent(activity, ContactListActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}
