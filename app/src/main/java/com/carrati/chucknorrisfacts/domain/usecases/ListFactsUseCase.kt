package com.carrati.chucknorrisfacts.domain.usecases

import com.carrati.chucknorrisfacts.domain.entities.Fact
import com.carrati.chucknorrisfacts.domain.repository.IFactsRepository
import io.reactivex.Scheduler
import io.reactivex.Single

class ListFactsUseCase (
    private val repository: IFactsRepository,
    private val scheduler: Scheduler
) {

    fun execute(): Single<List<Fact>> {
        return repository.listFacts().subscribeOn(scheduler)
    }

}