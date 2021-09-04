package com.example.jokeapp.data.dataSources.cache

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.UIJoke
import com.example.jokeapp.data.dataSources.cloud.JokeCloudDataSource

interface CacheDataSource {
    suspend fun addOrRemove(id: Int, joke: Joke): UIJoke

    suspend fun getJokeFromCache(): JokeCloudDataSource.Result
}