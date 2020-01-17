package com.infernal93.phonebookappmvvmanddagger.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.infernal93.phonebookappmvvmanddagger.entity.ContactsRoom

/**
 * Created by Armen Mkhitaryan on 11.01.2020.
 */

@Dao
interface ContactDao {

    @Insert
    fun insert(contactsRoom: ContactsRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contactsRoom: List<ContactsRoom>)

    @Update
    fun update(contactsRoom: ContactsRoom)

    @Delete
    fun delete(contactsRoom: ContactsRoom)

    @Query("DELETE FROM  custom_table")
    fun deleteAllContacts()

    @Query("SELECT * FROM custom_table ORDER BY priority DESC")
    fun getAllContact(): LiveData<List<ContactsRoom>>
}