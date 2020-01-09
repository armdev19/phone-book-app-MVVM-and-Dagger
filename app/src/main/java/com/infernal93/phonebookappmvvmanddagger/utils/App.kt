package com.infernal93.phonebookappmvvmanddagger.utils

import android.app.Application
import com.infernal93.phonebookappmvvmanddagger.di.components.ApplicationComponent
import com.infernal93.phonebookappmvvmanddagger.di.components.DaggerApplicationComponent

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class App : Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}