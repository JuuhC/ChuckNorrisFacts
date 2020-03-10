package com.carrati.chucknorrisfacts.data.remote.api

import com.carrati.chucknorrisfacts.data.remote.model.FactPayload
import io.reactivex.Single
import retrofit2.http.GET

interface IServerAPI {
    @GET("random")
    fun getFact(): Single<FactPayload>
}