package com.infernal93.phonebookappmvvmanddagger.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import com.infernal93.phonebookappmvvmanddagger.view.LoginListener
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 31.01.2020.
 */

class AuthViewModel @Inject constructor(private var mAuth: FirebaseAuth) : ViewModel() {
    private val TAG = "AuthViewModel"

    var mEmail: String = ""
    var mPassword: String = ""

    var loginListener: LoginListener? = null

    fun login(view: View) {
        if (mEmail.isNotEmpty() && mPassword.isNotEmpty()) {
            mAuth = FirebaseAuth.getInstance()
            loginListener?.startLoading()
            mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener {
                if (it.isSuccessful) {
                        loginListener?.endLoading()
                        loginListener?.validateLoginAndPassword()

                } else {
                    loginListener?.showError(textResource = R.string.login_error)
                }
            }
        } else {
            loginListener?.showError(textResource = R.string.login_or_password_empty)
        }
    }
}