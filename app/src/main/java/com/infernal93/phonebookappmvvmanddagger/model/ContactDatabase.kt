package com.infernal93.phonebookappmvvmanddagger.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.infernal93.phonebookappmvvmanddagger.data.local.ContactDao
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom

/**
 * Created by Armen Mkhitaryan on 11.01.2020.
 */

@Database(entities = [ContactsRoom::class], version = 1, exportSchema = true)
abstract class ContactDatabase  : RoomDatabase() {

    abstract fun contactDao() : ContactDao

    companion object{

        private var instance : ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if (instance == null) {
                synchronized(ContactDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java, "contact_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}