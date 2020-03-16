package com.carrati.chucknorrisfacts.data.remote.source

import com.carrati.chucknorrisfacts.data.remote.api.IServerAPI
import com.carrati.chucknorrisfacts.data.remote.mapper.FactsRemoteMapper
import com.carrati.chucknorrisfacts.domain.entities.Fact
import io.reactivex.Single

class FactsRemoteDataSourceImpl(private val serverAPI: IServerAPI): IFactsRemoteDataSource {

    override fun getFact(): Single<Fact> {
        return serverAPI.getFact().map { FactsRemoteMapper.mapFromAPI(it) }
    }
}