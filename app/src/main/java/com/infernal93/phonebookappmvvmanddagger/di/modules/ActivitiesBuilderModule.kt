package com.infernal93.phonebookappmvvmanddagger.di.modules

import com.infernal93.phonebookappmvvmanddagger.view.activities.AddContactActivity
import com.infernal93.phonebookappmvvmanddagger.view.activities.ContactListActivity
import com.infernal93.phonebookappmvvmanddagger.view.activities.AuthActivity
import com.infernal93.phonebookappmvvmanddagger.view.activities.DetailsActivity
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

    @ContributesAndroidInjector
    abstract fun bindingDetailsActivity() : DetailsActivity
}