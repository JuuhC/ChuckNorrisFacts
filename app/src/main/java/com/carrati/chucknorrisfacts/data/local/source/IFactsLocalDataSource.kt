package com.carrati.chucknorrisfacts.data.local.source

import com.carrati.chucknorrisfacts.domain.entities.Fact
import io.reactivex.Single

interface IFactsLocalDataSource {
    fun getFacts(): Single<List<Fact>>
    fun updateData(list: List<Fact>)
    fun insertData(fact: Fact)
}