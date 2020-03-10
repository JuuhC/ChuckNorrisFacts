package com.carrati.chucknorrisfacts.domain.entities

data class Fact (
    var id: String,
    var categories: List<String>,
    var url: String,
    var value: String
)