package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.api.JokeModelJSON

interface CacheDataSource {
    fun addOrRemove(id: Int, jokeModelJSON: JokeModelJSON): Joke

    fun getJokeFromCache(callback: JokeCachedCallback)
}