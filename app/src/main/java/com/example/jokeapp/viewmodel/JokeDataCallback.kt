package com.example.jokeapp.viewmodel

import androidx.annotation.DrawableRes

interface JokeDataCallback {

    fun updateText(text: String)

    fun updateIcon(@DrawableRes iconId: Int)
}