package com.example.jokeapp.data

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
    fun toJoke(): Joke{
        return Joke(setup, punchline)
    }
}