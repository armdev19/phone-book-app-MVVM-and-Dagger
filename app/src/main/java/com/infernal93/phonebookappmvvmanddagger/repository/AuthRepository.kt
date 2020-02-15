package com.infernal93.phonebookappmvvmanddagger.repository

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Created by Armen Mkhitaryan on 15.02.2020.
 */

class AuthRepository @Inject constructor(private var mAuth: FirebaseAuth): ViewModel() {

    fun signOut() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
    }

}