package com.infernal93.phonebookappmvvmanddagger.di.modules

import com.infernal93.phonebookappmvvmanddagger.view.activities.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Armen Mkhitaryan on 31.01.2020.
 */

@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindingContactListActivity() : ContactListActivity

    @ContributesAndroidInjector
    abstract fun bindingAddContactActivity() : AddContactActivity

    @ContributesAndroidInjector(modules = [FragmentsBuilderModule::class])
    abstract fun bindingLoginActivity() : AuthActivity

    @ContributesAndroidInjector(modules = [FragmentsBuilderModule::class])
    abstract fun bindingDetailsActivity() : DetailsActivity

    @ContributesAndroidInjector
    abstract fun bindingUpdateDataActivity() : UpdateDataActivity
}