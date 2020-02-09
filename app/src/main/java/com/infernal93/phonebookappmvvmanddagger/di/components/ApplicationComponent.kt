package com.infernal93.phonebookappmvvmanddagger.di.components

import com.infernal93.phonebookappmvvmanddagger.App
import com.infernal93.phonebookappmvvmanddagger.di.modules.AppModule
import com.infernal93.phonebookappmvvmanddagger.di.modules.NetworkModule
import com.infernal93.phonebookappmvvmanddagger.di.modules.RoomModule
import com.infernal93.phonebookappmvvmanddagger.data.local.ContactDao
import com.infernal93.phonebookappmvvmanddagger.data.remote.ContactsService
import com.infernal93.phonebookappmvvmanddagger.data.remote.ImagesService
import com.infernal93.phonebookappmvvmanddagger.di.modules.ViewModelModule
import com.infernal93.phonebookappmvvmanddagger.model.ContactDatabase
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, NetworkModule::class,
                      RoomModule::class, ViewModelModule::class])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App>



}