package com.example.jokeapp.data

import com.example.jokeapp.data.api.JokeModelJSON
import retrofit2.Call
import retrofit2.http.GET

interface OldJokeLoader{
    fun getJoke(callback: DownloadCallback)
}

interface RetrofitJokeLoader{
    @GET("https://official-joke-api.appspot.com/random_joke/")
    fun getJoke(): Call<JokeModelJSON>
}

interface DownloadCallback{
    fun returnSuccess(data: JokeModelJSON)

    fun returnError(type: ErrorType)
}

enum class ErrorType{
    NO_CONNECTION,
    OTHER
}