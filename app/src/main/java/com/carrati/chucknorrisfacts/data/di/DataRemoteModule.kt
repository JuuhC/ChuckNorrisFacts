package com.carrati.chucknorrisfacts.data.di

import com.carrati.chucknorrisfacts.R
import com.carrati.chucknorrisfacts.data.remote.api.IServerAPI
import com.carrati.chucknorrisfacts.data.remote.api.RetrofitManager
import com.carrati.chucknorrisfacts.data.remote.source.FactsRemoteDataSourceImpl
import com.carrati.chucknorrisfacts.data.remote.source.IFactsRemoteDataSource
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


val remoteDataModule = module {
    single { RetrofitManager().createWebService<IServerAPI>(
            url =  androidContext().getString(R.string.base_url)
    ) }

    factory<IFactsRemoteDataSource> { FactsRemoteDataSourceImpl( serverAPI = get()) }
}