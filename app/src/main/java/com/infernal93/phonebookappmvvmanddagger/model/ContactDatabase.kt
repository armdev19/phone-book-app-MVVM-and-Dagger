package com.infernal93.phonebookappmvvmanddagger.model

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db) }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                //PopulateDbAsyncTask(instance).execute()
            }
        }

//        class PopulateDbAsyncTask (db: ContactDatabase?) : AsyncTask<Unit, Unit, Unit>() {
//
//           // private val contactDao = db?.contactDao()
//
//            override fun doInBackground(vararg param: Unit?) {
//                //contactDao?.insert(ContactsRoom(priority = 1, firstName = "vasya", lastName = "pupkin",
//                //  phone = "88522255", email = "sddddd", notes = "dsdssdsd",
//                //image = "https://cdn25.img.ria.ru/images/155072/77/1550727792_0:147:2566:1590_600x0_80_0_0_0b66e7f16c722eab42377301f30e865b.jpg"))
//            }
//        }
    }
}