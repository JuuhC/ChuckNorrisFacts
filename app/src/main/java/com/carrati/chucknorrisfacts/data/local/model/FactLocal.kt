package com.carrati.chucknorrisfacts.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts_conf")
data class FactLocal (
    @PrimaryKey(autoGenerate = false)
    var categories: String,
    var id: String,
    var url: String,
    var value: String
)