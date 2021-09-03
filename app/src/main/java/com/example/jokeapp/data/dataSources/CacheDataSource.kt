package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.UIJoke
import com.example.jokeapp.data.api.JokeModelJSON

interface CacheDataSource {
    fun addOrRemove(id: Int, joke: Joke): UIJoke

    fun getJokeFromCache(callback: JokeCachedCallback)
}