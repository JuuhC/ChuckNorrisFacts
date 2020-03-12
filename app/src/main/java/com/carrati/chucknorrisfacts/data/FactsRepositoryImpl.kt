package com.carrati.chucknorrisfacts.data

import android.util.Log
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

    override fun getRandomFact(): Single<Fact> {
        return remoteDataSource.getFact().flatMap { fact ->
                Log.e("Repo", fact.toString())
                localDataSource.insertData( fact )
                Single.just(fact)
            }

    }
}