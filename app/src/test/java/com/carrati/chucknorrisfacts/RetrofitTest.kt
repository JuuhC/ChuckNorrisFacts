package com.carrati.chucknorrisfacts

import com.carrati.chucknorrisfacts.data.remote.api.IServerAPI
import com.carrati.chucknorrisfacts.data.remote.api.RetrofitManager
import com.carrati.chucknorrisfacts.data.remote.model.FactPayload
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RetrofitTest {

    @Test
    fun `test get random fact with correct url`(){
        val api = RetrofitManager().createWebService<IServerAPI>("https://api.chucknorris.io/jokes/")
        val response = api.getFact().blockingGet()

        Assert.assertTrue(response is FactPayload)
    }

    @Test(expected = Throwable::class)
    fun `test get random fact with incorrect url`(){
        val api = RetrofitManager().createWebService<IServerAPI>("https://api.chucknorris.io/jokez/")
        val response = api.getFact().blockingGet()
    }
}