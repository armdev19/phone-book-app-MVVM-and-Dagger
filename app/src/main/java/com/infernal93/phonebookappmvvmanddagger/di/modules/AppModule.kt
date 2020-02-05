package com.infernal93.phonebookappmvvmanddagger.di.modules

import android.content.Context
import com.infernal93.phonebookappmvvmanddagger.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Module(includes = [ActivitiesBuilderModule::class, FragmentsBuilderModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App) : Context {
        return app.applicationContext
    }
}