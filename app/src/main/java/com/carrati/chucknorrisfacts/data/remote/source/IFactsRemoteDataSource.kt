package com.carrati.chucknorrisfacts.data.remote.source

import com.carrati.chucknorrisfacts.domain.entities.Fact
import io.reactivex.Single

interface IFactsRemoteDataSource {
    fun getFact(): Single<Fact>
}