package com.infernal93.phonebookappmvvmanddagger.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infernal93.phonebookappmvvmanddagger.di.key.ViewModelKey
import com.infernal93.phonebookappmvvmanddagger.viewmodels.*
import dagger.Binds
import dagger.Module
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

    @Binds
    @IntoMap
    @ViewModelKey(value = DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = EditContactViewModel::class)
    abstract fun bindEditContactViewModel(editContactViewModel: EditContactViewModel) : ViewModel

    }


