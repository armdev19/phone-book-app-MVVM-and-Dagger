package com.infernal93.phonebookappmvvmanddagger.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

data class ContactsApi(
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("notes")
    var notes: String,
    @SerializedName("images")
    var images: String?
) : Serializable