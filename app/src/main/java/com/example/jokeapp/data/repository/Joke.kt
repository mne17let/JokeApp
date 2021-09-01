package com.example.jokeapp.data

import androidx.annotation.DrawableRes
import com.example.jokeapp.R
import com.example.jokeapp.viewmodel.JokeDataCallback

abstract class Joke(private val setup: String, private val punchline: String) {

    protected fun getJokeForUi(): String{
        return "$setup\n$punchline"
    }

    @DrawableRes
    protected abstract fun getLikeImageId(): Int

    fun doMap(dataCallback: JokeDataCallback){
        dataCallback.updateText(getJokeForUi())
        dataCallback.updateIcon(getLikeImageId())
    }
}

class StandardJoke(setup: String, punchline: String): Joke(setup, punchline){
    override fun getLikeImageId(): Int {
        return R.drawable.dont_like_heart
    }
}

class FavouriteJoke(setup: String, punchline: String): Joke(setup, punchline){
    override fun getLikeImageId(): Int {
        return R.drawable.like_heart
    }
}

class FailedJoke(text: String): Joke(text, ""){
    override fun getLikeImageId(): Int {
        return 0
    }
}