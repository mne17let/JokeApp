package com.example.jokeapp.data.api

import com.example.jokeapp.data.FailedJoke
import com.example.jokeapp.data.FavouriteJoke
import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.StandardJoke
import com.example.jokeapp.data.dataSources.CacheDataSource
import com.google.gson.annotations.SerializedName


data class JokeModelJSON(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("type")
    private val type: String,
    @SerializedName("setup")
    private val setup: String,
    @SerializedName("punchline")
    private val punchline: String
){
    fun checkForCache(cacheDataSource: CacheDataSource): Joke{
        return cacheDataSource.addOrRemove(id, this)
    }

    fun toStandardJoke(): Joke{
        return StandardJoke(setup, punchline)
    }

    fun toFavouriteJoke(): Joke{
        return FavouriteJoke(setup, punchline)
    }

    fun toFailedJoke(): Joke{
        return FailedJoke("Ошибочная шутка")
    }
}
