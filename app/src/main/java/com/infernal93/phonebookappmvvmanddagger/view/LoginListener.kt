package com.infernal93.phonebookappmvvmanddagger.view

/**
 * Created by Armen Mkhitaryan on 04.02.2020.
 */
interface LoginListener {

    fun startLoading()
    fun endLoading()
    fun showError(textResource: Int)
    fun validateLoginAndPassword()
}