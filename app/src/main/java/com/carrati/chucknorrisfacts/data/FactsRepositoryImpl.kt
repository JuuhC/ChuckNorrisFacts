package com.carrati.chucknorrisfacts.data

import com.carrati.chucknorrisfacts.data.local.source.IFactsLocalDataSource
import com.carrati.chucknorrisfacts.data.remote.source.IFactsRemoteDataSource
import com.carrati.chucknorrisfacts.domain.entities.Fact
import com.carrati.chucknorrisfacts.domain.repository.IFactsRepository
import io.reactivex.Single

class FactsRepositoryImpl(
    private val localDataSource: IFactsLocalDataSource,
    private val remoteDataSource: IFactsRemoteDataSource
): IFactsRepository {

    override fun listFacts(): Single<List<Fact>> {
        return localDataSource.getFacts()
    }

    override fun getRandomFact(): Single<List<Fact>> {
        return remoteDataSource.getFact().flatMap { fact ->
                localDataSource.insertData( fact )
                localDataSource.getFacts()
            }

    }
}