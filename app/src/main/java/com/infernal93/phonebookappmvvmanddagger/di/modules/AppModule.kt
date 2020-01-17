package com.infernal93.phonebookappmvvmanddagger.di.modules

import android.app.Application
import com.infernal93.phonebookappmvvmanddagger.App
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * Created by Armen Mkhitaryan on 12.01.2020.
 */

@Module(includes = [AndroidInjectionModule::class])
        abstract class AppModule {

    @Binds
    @Singleton
    // Singleton annotation isn't necessary (in this case since Application instance is unique)
    // but is here for convention.
    abstract fun application(app: App?): Application?
}