package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.api.JokeModelJSON

interface JokeCachedCallback {
    fun cachedSuccessfully(joke: Joke)

    fun cacheError()
}