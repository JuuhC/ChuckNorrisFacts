package com.carrati.chucknorrisfacts.data.di

import androidx.room.Room
import com.carrati.chucknorrisfacts.data.local.database.FactsDatabase
import com.carrati.chucknorrisfacts.data.local.source.FactsLocalDataSourceImpl
import com.carrati.chucknorrisfacts.data.local.source.IFactsLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataModule = module {
    single { FactsDatabase.getDatabase(androidContext()) }
    single { get<FactsDatabase>().factsDAO() }
    factory<IFactsLocalDataSource> { FactsLocalDataSourceImpl(factsDAO = get()) }
}