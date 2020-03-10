package com.carrati.chucknorrisfacts.data.remote.mapper

import com.carrati.chucknorrisfacts.data.remote.model.FactPayload
import com.carrati.chucknorrisfacts.domain.entities.Fact

object FactsRemoteMapper {

    fun mapFromAPI(fact: FactPayload) = Fact(
        id = fact.id,
        categories = fact.categories,
        url = fact.url,
        value = fact.value
    )
}