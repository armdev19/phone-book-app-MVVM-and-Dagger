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
    abstract fun bindContactListActivity() : ContactListActivity

    @ContributesAndroidInjector
    abstract fun bindAddContactActivity() : AddContactActivity

    @ContributesAndroidInjector
    abstract fun bindDetailsActivity() : DetailsActivity

    @ContributesAndroidInjector
    abstract fun bindEditContactActivity() : EditContactActivity

    @ContributesAndroidInjector(modules = [FragmentsBuilderModule::class])
    abstract fun bindAuthActivity() : AuthActivity
}