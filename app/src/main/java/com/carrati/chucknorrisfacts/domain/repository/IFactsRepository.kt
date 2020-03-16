package com.carrati.chucknorrisfacts.domain.repository

import com.carrati.chucknorrisfacts.domain.entities.Fact
import io.reactivex.Single

interface IFactsRepository {
    fun listFacts(): Single<List<Fact>>
    fun getRandomFact(): Single<Fact>
}