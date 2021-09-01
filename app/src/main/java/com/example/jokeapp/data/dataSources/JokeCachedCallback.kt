package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.api.JokeModelJSON

interface JokeCachedCallback {
    fun cachedSuccessfully(jokeModelJSON: JokeModelJSON)

    fun cacheError()
}