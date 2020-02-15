package com.infernal93.phonebookappmvvmanddagger.view.interfaces

/**
 * Created by Armen Mkhitaryan on 04.02.2020.
 */
interface LoginListener {
    fun startLoading()
    fun endLoading()
    fun showError(textResource: Int)
    fun showError(textResource: String)
    fun validateLoginAndPassword()
}