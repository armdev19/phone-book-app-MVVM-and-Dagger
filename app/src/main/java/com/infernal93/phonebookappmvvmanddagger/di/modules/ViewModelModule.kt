package com.infernal93.phonebookappmvvmanddagger.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infernal93.phonebookappmvvmanddagger.di.ViewModelKey
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactsViewModel
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    abstract fun bindViewModel(viewModel: ContactsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

