package com.infernal93.phonebookappmvvmanddagger.view.interfaces

/**
 * Created by Armen Mkhitaryan on 12.02.2020.
 */
interface ErrorListener {

    fun showMessage(textResource: String)
    fun showError(textResource: Int)

}