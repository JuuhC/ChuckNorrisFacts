package com.carrati.chucknorrisfacts.utils

class CardTextEditor {

    fun getCardTextSize(joke: String): Float{
        return if(joke.length >= 80) 22F else 28F
    }
}