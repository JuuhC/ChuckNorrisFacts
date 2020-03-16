package com.carrati.chucknorrisfacts.data.local.source

import com.carrati.chucknorrisfacts.data.local.database.IFactsDAO
import com.carrati.chucknorrisfacts.data.local.mapper.FactsLocalMapper
import com.carrati.chucknorrisfacts.domain.entities.Fact
import io.reactivex.Single

class FactsLocalDataSourceImpl(private val factsDAO: IFactsDAO): IFactsLocalDataSource {

    override fun getFacts(): Single<List<Fact>> {
        return factsDAO.getFacts().map{ FactsLocalMapper.mapFromDB(it) }
    }

    override fun updateData(list: List<Fact>) {
        return factsDAO.updateData( FactsLocalMapper.mapToDB(list) )
    }

    override fun insertData(fact: Fact) {
        return factsDAO.insertOne( FactsLocalMapper.mapToDB(fact) )
    }
}