package com.example.jokeapp.data

class Joke(private val setup: String, private val punchline: String) {

    fun getJokeForUi(): String{
        return "$setup\n$punchline"
    }
}