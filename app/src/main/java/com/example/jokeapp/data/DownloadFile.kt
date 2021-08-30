package com.example.jokeapp.data

interface JokeLoader{
    fun getJoke(callback: DownloadCallback)
}

interface DownloadCallback{
    fun returnSuccess(data: JokeModelJSON)

    fun returnError(type: ErrorType)
}

enum class ErrorType{
    NO_CONNECTION,
    OTHER
}