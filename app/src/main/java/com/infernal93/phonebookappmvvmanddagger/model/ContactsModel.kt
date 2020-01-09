package com.infernal93.phonebookappmvvmanddagger.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

data class ContactsModel(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("images")
    val images: String?
) : Serializable