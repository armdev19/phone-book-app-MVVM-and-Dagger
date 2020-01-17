package com.infernal93.phonebookappmvvmanddagger

import android.app.Application
import com.infernal93.phonebookappmvvmanddagger.di.components.DaggerApplicationComponent
import com.infernal93.phonebookappmvvmanddagger.di.modules.ContextModule


/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

class App : Application() {

        val appComponent = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this))
            .build()!!
}