package com.carrati.chucknorrisfacts.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carrati.chucknorrisfacts.data.local.model.FactLocal
import io.reactivex.Single

@Dao
interface IFactsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(fact: FactLocal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun updateData(facts: List<FactLocal>)

    @Query("DELETE FROM facts_conf")
    fun deleteAll()

    @Query("SELECT * FROM facts_conf")
    fun getFacts(): Single<List<FactLocal>>
}