package com.infernal93.phonebookappmvvmanddagger.di.modules

import androidx.room.Room
import com.infernal93.phonebookappmvvmanddagger.data.local.ContactDao
import com.infernal93.phonebookappmvvmanddagger.model.ContactDatabase
import com.infernal93.phonebookappmvvmanddagger.repository.RoomRepository
import com.infernal93.phonebookappmvvmanddagger.App
import com.infernal93.phonebookappmvvmanddagger.repository.ApiRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Armen Mkhitaryan on 12.01.2020.
 */
@Module
class RoomModule {

    private lateinit var contactDatabase: ContactDatabase

    fun RoomModule(app: App) {
        contactDatabase = Room.databaseBuilder<ContactDatabase>(app, ContactDatabase::class.java, "contact_database")
                .build()
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(): ContactDatabase {
        return contactDatabase
    }

    @Singleton
    @Provides
    fun providesContactDao(contactDatabase: ContactDatabase): ContactDao {
        return contactDatabase.contactDao()
    }



    @Provides
    fun providesContactRepository(app: App, apiRepository: ApiRepository) : RoomRepository {
        return RoomRepository(
            app,
            apiRepository
        )
    }

}