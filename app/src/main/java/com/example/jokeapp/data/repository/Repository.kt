package com.example.jokeapp.data.repository

import com.example.jokeapp.data.UIJoke

interface Repository {

    suspend fun getJoke(): UIJoke

    fun start(resultCallback: ResultCallback)

    fun clear()
}