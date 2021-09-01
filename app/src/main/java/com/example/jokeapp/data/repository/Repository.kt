package com.example.jokeapp.data.repository

interface Repository<S, E> {

    fun getJoke()

    fun start(resultCallback: ResultCallback)

    fun clear()
}