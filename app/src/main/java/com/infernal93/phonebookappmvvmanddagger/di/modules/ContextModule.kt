package com.infernal93.phonebookappmvvmanddagger.di.modules

import com.infernal93.phonebookappmvvmanddagger.App
import dagger.Module
import dagger.Provides

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Module
class ContextModule(val app: App) {

    @Provides
    fun provideApplication(): App {
        return app
    }
}