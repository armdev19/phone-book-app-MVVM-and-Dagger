package com.infernal93.phonebookappmvvmanddagger.di.components

import com.infernal93.phonebookappmvvmanddagger.di.modules.ContextModule
import com.infernal93.phonebookappmvvmanddagger.di.modules.NetworkModule
import com.infernal93.phonebookappmvvmanddagger.view.ContactListActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Singleton
@Component(modules = [NetworkModule::class, ContextModule::class])
interface ApplicationComponent {

    fun inject(activity: ContactListActivity)

}