package com.carrati.chucknorrisfacts.domain.di

import com.carrati.chucknorrisfacts.domain.usecases.GetRandomFactUseCase
import com.carrati.chucknorrisfacts.domain.usecases.ListFactsUseCase
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        ListFactsUseCase(
            repository = get(),
            scheduler = Schedulers.io()
        )
    }

    factory {
        GetRandomFactUseCase(
            repository = get(),
            scheduler = Schedulers.io()
        )
    }
}

val domainModule = listOf(useCaseModule)