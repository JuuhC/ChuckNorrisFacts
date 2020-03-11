package com.carrati.chucknorrisfacts

import android.app.Application
import com.carrati.chucknorrisfacts.data.di.dataModules
import com.carrati.chucknorrisfacts.domain.di.domainModule
import com.carrati.chucknorrisfacts.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyModuleApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyModuleApplication)
            modules(domainModule + dataModules + listOf(presentationModule))
        }
    }

}