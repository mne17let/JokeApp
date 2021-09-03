package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.api.JokeModelJSON

interface CloudCallback {
    fun onError(errorType: ErrorType)

    fun onSuccess(joke: Joke)
}

enum class ErrorType{
    NO_CONNECTION,
    SERVICE_UNAVAILABLE
}