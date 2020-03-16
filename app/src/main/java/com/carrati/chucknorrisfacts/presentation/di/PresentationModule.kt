package com.carrati.chucknorrisfacts.presentation.di

import com.carrati.chucknorrisfacts.presentation.adapters.FactsAdapter
import com.carrati.chucknorrisfacts.presentation.viewmodels.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    factory { FactsAdapter() }

    viewModel {
        MainViewModel(
            listFactsUC = get(),
            getRandomFactUC = get(),
            uiScheduler = AndroidSchedulers.mainThread()
        )
    }
}