package com.carrati.chucknorrisfacts.data.local.mapper

import com.carrati.chucknorrisfacts.data.local.model.FactLocal
import com.carrati.chucknorrisfacts.domain.entities.Fact

object FactsLocalMapper {

    fun mapFromDB(data: List<FactLocal>) = data.map { mapToDomain(it) }

    private fun mapToDomain(data: FactLocal) = Fact(
        id = data.id,
        categories = data.categories.split(","),
        url = data.url,
        value = data.value
    )

    fun mapToDB(data: List<Fact>) = data.map { mapFromDomain(it) }

    private fun mapFromDomain(data: Fact) = FactLocal(
        id = data.id,
        categories = data.categories.joinToString(","),
        url = data.url,
        value = data.value
    )

    fun mapToDB(data: Fact) = mapFromDomain(data)
}