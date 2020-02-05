package com.infernal93.phonebookappmvvmanddagger.di.modules

import com.infernal93.phonebookappmvvmanddagger.view.fragments.LoginFragment
import com.infernal93.phonebookappmvvmanddagger.view.fragments.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Armen Mkhitaryan on 31.01.2020.
 */

@Module
abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindingLoginFragment() : LoginFragment
    abstract fun bindingRegisterFragment() : RegisterFragment

}