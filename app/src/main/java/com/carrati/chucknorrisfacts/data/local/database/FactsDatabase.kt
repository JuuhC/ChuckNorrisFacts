package com.carrati.chucknorrisfacts.data.local.database

import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carrati.chucknorrisfacts.data.local.model.FactLocal

@Database(entities = [FactLocal::class], version = 1, exportSchema = false)
abstract class FactsDatabase: RoomDatabase() {
    abstract fun factsDAO(): IFactsDAO

    companion object {
        @Volatile
        private var INSTANCE: FactsDatabase? = null
        var TEST_MODE = false

        fun getDatabase (context: Context): FactsDatabase? {
            if (this.INSTANCE != null) {
                return this.INSTANCE
            } else {
                synchronized(this) {
                    val instance: FactsDatabase
                    if(TEST_MODE) {
                        instance = Room.inMemoryDatabaseBuilder(context,FactsDatabase::class.java)
                            .allowMainThreadQueries().build()
                    } else {
                        instance =
                            Room.databaseBuilder(context, FactsDatabase::class.java, "facts.db")
                                //.allowMainThreadQueries()
                                .build()
                    }
                    this.INSTANCE = instance
                    return instance
                }
            }
        }
    }
}