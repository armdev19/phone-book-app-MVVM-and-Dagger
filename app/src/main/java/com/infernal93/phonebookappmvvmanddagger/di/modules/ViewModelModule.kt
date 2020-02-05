package com.infernal93.phonebookappmvvmanddagger.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infernal93.phonebookappmvvmanddagger.di.key.ViewModelKey
import com.infernal93.phonebookappmvvmanddagger.viewmodels.AddContactViewModel
import com.infernal93.phonebookappmvvmanddagger.viewmodels.AuthViewModel
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactListViewModel
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(value = AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = ContactListViewModel::class)
    abstract fun bindContactListViewModel(contactListViewModel: ContactListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = AddContactViewModel::class)
    abstract fun bindAddContactViewModel(addContactViewModel: AddContactViewModel): ViewModel

    }


