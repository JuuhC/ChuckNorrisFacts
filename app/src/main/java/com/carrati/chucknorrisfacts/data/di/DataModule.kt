package com.carrati.chucknorrisfacts.data.di

import com.carrati.chucknorrisfacts.data.FactsRepositoryImpl
import com.carrati.chucknorrisfacts.domain.repository.IFactsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<IFactsRepository> {
        FactsRepositoryImpl(
                localDataSource = get(),
                remoteDataSource = get()
        )
    }
}

val dataModules = listOf(remoteDataModule, repositoryModule, localDataModule)