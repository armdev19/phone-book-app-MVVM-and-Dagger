package com.infernal93.phonebookappmvvmanddagger

import com.infernal93.phonebookappmvvmanddagger.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }
}