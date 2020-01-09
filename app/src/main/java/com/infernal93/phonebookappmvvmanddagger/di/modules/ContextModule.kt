package com.infernal93.phonebookappmvvmanddagger.di.modules

import android.content.Context
import com.infernal93.phonebookappmvvmanddagger.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Module
class ContextModule
{
    @Provides
    @Singleton
    fun provideApplication(app : App): Context = app
}