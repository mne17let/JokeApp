package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.StandardJoke
import com.example.jokeapp.data.api.JokeModelJSON

class JokeCacheDataSource: CacheDataSource {

    private val list = ArrayList<Pair<Int, JokeModelJSON>>()

    private val map = HashMap<Int, JokeModelJSON>()

    override fun addOrRemove(id: Int, jokeModelJSON: JokeModelJSON): Joke {
        val found = list.find {
            it.first == id
        }

        val result = if(found != null){
            val removedJoke = found.second.toStandardJoke()
            list.remove(found)
            removedJoke
        } else{
            list.add(Pair(id, jokeModelJSON))
            jokeModelJSON.toFavouriteJoke()
        }
        return result
    }

    override fun getJokeFromCache(callback: JokeCachedCallback) {
        if (list.isEmpty()){
            callback.cacheError()
        } else{
            callback.cachedSuccessfully(list.random().second)
        }
    }
}