package com.example.jokeapp.data

interface Model<S, E> {

    fun getJoke()

    fun start(resultCallback: ResultCallback<S, E>)

    fun clear()
}