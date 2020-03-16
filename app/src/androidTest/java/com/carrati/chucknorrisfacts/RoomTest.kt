package com.carrati.chucknorrisfacts

import com.carrati.chucknorrisfacts.data.local.database.FactsDatabase
import com.carrati.chucknorrisfacts.data.local.database.IFactsDAO
import org.junit.Before
import org.junit.runner.RunWith
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carrati.chucknorrisfacts.data.local.model.FactLocal
import org.junit.Assert
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private var factsDao: IFactsDAO? = null

    @Before
    fun setup(){
        FactsDatabase.TEST_MODE = true
        factsDao = FactsDatabase.getDatabase(ApplicationProvider.getApplicationContext())?.factsDAO()
    }

    @Test
    fun should_insert_fact(){
        val fact = FactLocal(
            id = "do2ZIeeNREm7HRsDq59yWg",
            categories = "",
            url = "https://api.chucknorris.io/jokes/do2ZIeeNREm7HRsDq59yWg",
            value = "success"
            )

        factsDao!!.insertOne(fact)
        val factTest = factsDao!!.getFacts().blockingGet()
        Assert.assertTrue(factTest.contains(fact))
    }

    @Test
    fun should_get_all_facts(){
        should_insert_fact()

        val factsList = factsDao!!.getFacts().blockingGet()
        Assert.assertTrue(factsList.size > 0)
    }

    @Test
    fun should_delete_all_facts(){
        factsDao!!.deleteAll()
        val factsList = factsDao!!.getFacts().blockingGet()
        Assert.assertTrue(factsList.size == 0)
    }
}