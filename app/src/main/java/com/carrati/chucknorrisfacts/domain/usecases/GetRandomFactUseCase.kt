package com.carrati.chucknorrisfacts.domain.usecases

import com.carrati.chucknorrisfacts.domain.entities.Fact
import com.carrati.chucknorrisfacts.domain.repository.IFactsRepository
import io.reactivex.Scheduler
import io.reactivex.Single

class GetRandomFactUseCase(
    private val repository: IFactsRepository,
    private val scheduler: Scheduler
) {

    fun execute(): Single<List<Fact>> {
        return repository.getRandomFact().subscribeOn(scheduler)
    }

}