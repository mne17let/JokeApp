package com.example.jokeapp.data

import androidx.annotation.DrawableRes
import com.example.jokeapp.R
import com.example.jokeapp.data.dataSources.CacheDataSource
import com.example.jokeapp.data.dataSources.database.DataBaseJokeModel
import com.example.jokeapp.viewmodel.JokeDataCallback

class Joke(private val id: Int,
           private val type: String,
           private val setup: String,
           private val punchline: String) {

    fun checkForCache(cacheDataSource: CacheDataSource): UIJoke{
        return cacheDataSource.addOrRemove(id, this)
    }

    fun toStandardJoke(): StandardJoke{
        return StandardJoke(setup, punchline)
    }

    fun toFavouriteJoke(): FavouriteJoke{
        return FavouriteJoke(setup, punchline)
    }

    fun toFailedJoke(): FailedJoke{
        return FailedJoke("Ошибочная шутка")
    }

    fun toRealmJoke(): DataBaseJokeModel {
        return DataBaseJokeModel().also {
            it.id = id
            it.type = type
            it.setup = setup
            it.punchline = punchline
        }
    }
}

abstract class UIJoke(private val setup: String, private val punchline: String){
    fun getJokeForUI(): String{
        return "$setup\n$punchline"
    }

    fun doMap(dataCallback: JokeDataCallback){
        dataCallback.updateText(getJokeForUI())
        dataCallback.updateIcon(getLikeImageId())
    }

    @DrawableRes
    protected abstract fun getLikeImageId(): Int
}

class StandardJoke(setup: String, punchline: String): UIJoke(setup, punchline){
    override fun getLikeImageId(): Int {
        return R.drawable.dont_like_heart
    }
}

class FavouriteJoke(setup: String, punchline: String): UIJoke(setup, punchline){
    override fun getLikeImageId(): Int {
        return R.drawable.like_heart
    }
}

class FailedJoke(text: String): UIJoke(text, ""){
    override fun getLikeImageId(): Int {
        return 0
    }
}