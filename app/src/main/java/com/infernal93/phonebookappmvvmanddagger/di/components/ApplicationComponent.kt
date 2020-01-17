package com.infernal93.phonebookappmvvmanddagger.di.components

import com.infernal93.phonebookappmvvmanddagger.App
import com.infernal93.phonebookappmvvmanddagger.di.modules.ContextModule
import com.infernal93.phonebookappmvvmanddagger.di.modules.NetworkModule
import com.infernal93.phonebookappmvvmanddagger.di.modules.RoomModule
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import com.infernal93.phonebookappmvvmanddagger.data.local.ContactDao
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.model.ContactDatabase
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import com.infernal93.phonebookappmvvmanddagger.view.activities.AddContactActivity
import com.infernal93.phonebookappmvvmanddagger.view.activities.ContactListActivity
import com.infernal93.phonebookappmvvmanddagger.viewmodels.ContactsViewModel
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Singleton
@Component(modules = [ContextModule::class, NetworkModule::class, RoomModule::class])
interface ApplicationComponent {

    fun inject(app: App)

    // Activities
    fun inject(activity: ContactListActivity)
    fun inject(activity: AddContactActivity)

    // ViewModels
    fun inject(viewModel: ContactsViewModel)

    fun inject(apiRepository: ApiRepository)

    fun contactDatabase(): ContactDatabase

    fun contactDao(): ContactDao

    fun contactRepository(): RoomRepository

    fun contactsService() : ContactsService
    fun imagesService() : ImagesService


}