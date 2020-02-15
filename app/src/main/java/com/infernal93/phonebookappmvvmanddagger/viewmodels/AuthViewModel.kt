package com.infernal93.phonebookappmvvmanddagger.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.infernal93.phonebookappmvvmanddagger.R
import com.infernal93.phonebookappmvvmanddagger.view.interfaces.LoginListener
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 31.01.2020.
 */

class AuthViewModel @Inject constructor(private var mAuth: FirebaseAuth) : ViewModel() {

    var mLoginListener: LoginListener? = null
    var mEmail: String = ""
    var mPassword: String = ""


    fun login() {
        when {
            mEmail.trim().isEmpty() -> {
                mLoginListener?.showError(textResource = R.string.login_is_empty)
            }
            !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches() -> {
                mLoginListener?.showError(textResource = R.string.login_invalid)
            }
            mPassword.trim().isEmpty() -> {
                mLoginListener?.showError(textResource = R.string.password_is_empty)
            }
            mPassword.length < 8 -> {
                mLoginListener?.showError(textResource = R.string.password_invalid)
            }
            mEmail.isNotEmpty() && mPassword.isNotEmpty() -> {

                mLoginListener?.startLoading()
                mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener {
                    if (it.isSuccessful) {
                        mLoginListener?.endLoading()
                        mLoginListener?.validateLoginAndPassword()
                    } else {
                        mLoginListener?.endLoading()
                        mLoginListener?.showError(textResource = it.exception.toString())
                    }
                }
            }
        }
    }
}