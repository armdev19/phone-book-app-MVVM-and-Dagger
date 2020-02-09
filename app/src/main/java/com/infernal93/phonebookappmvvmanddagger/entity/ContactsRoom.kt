package com.infernal93.phonebookappmvvmanddagger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Armen Mkhitaryan on 11.01.2020.
 */

@Entity(tableName = "custom_table")
data class ContactsRoom(
    @PrimaryKey(autoGenerate = true)
    var priority: Int? = null,

    @ColumnInfo(name = "Id")
    var _id: String = "",

    @ColumnInfo(name = "FirstName")
    var firstName : String? = null,

    @ColumnInfo(name = "LastName")
    var lastName : String? = null,

    @ColumnInfo(name = "Phone")
    var phone : String? = null,

    @ColumnInfo(name = "Email")
    var email : String? = null,

    @ColumnInfo(name = "Notes")
    var notes : String? = null,

    @ColumnInfo(name = "Images")
    var images: String? = "") : Serializable