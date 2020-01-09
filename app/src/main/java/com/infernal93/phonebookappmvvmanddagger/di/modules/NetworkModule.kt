package com.infernal93.phonebookappmvvmanddagger.di.modules

import com.infernal93.phonebookappmvvmanddagger.remote.ContactsService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

/**
 * Created by Armen Mkhitaryan on 09.01.2020.
 */

@Module(includes = [ViewModelModule::class])
class NetworkModule {

    companion object {
        const val KEY = "5e0fc647b68f0802dd3e5f8b"
        const val BASE_URL = "https://phonebookapp-683c.restdb.io/rest/"
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    //.addQueryParameter("apikey", KEY)
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)
                    .header("apikey", KEY)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        // logging interceptor
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttp())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideContactsService(retrofit: Retrofit) : ContactsService{

        return retrofit.create(ContactsService::class.java)
    }

}