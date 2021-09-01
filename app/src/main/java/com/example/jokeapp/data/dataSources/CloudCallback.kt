package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.api.JokeModelJSON

interface CloudCallback {
    fun onError(errorType: ErrorType)

    fun onSuccess(jsonJoke: JokeModelJSON)
}

enum class ErrorType{
    NO_CONNECTION,
    SERVICE_UNAVAILABLE
}