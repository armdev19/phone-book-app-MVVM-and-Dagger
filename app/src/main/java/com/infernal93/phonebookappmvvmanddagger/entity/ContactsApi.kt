package com.infernal93.phonebookappmvvmanddagger.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

data class ContactsApi(
    @SerializedName(value = "firstName")
    var firstName: String,
    @SerializedName(value = "lastName")
    var lastName: String,
    @SerializedName(value = "phone")
    var phone: String,
    @SerializedName(value = "email")
    var email: String,
    @SerializedName(value = "notes")
    var notes: String,
    @SerializedName(value = "images")
    var images: String?
) : Serializable