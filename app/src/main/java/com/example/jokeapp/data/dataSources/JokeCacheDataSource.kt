package com.example.jokeapp.data.dataSources

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.StandardJoke
import com.example.jokeapp.data.UIJoke
import com.example.jokeapp.data.api.JokeModelJSON

class JokeCacheDataSource: CacheDataSource {

    private val list = ArrayList<Pair<Int, Joke>>()

    private val map = HashMap<Int, JokeModelJSON>()

    override fun addOrRemove(id: Int, joke: Joke): UIJoke {
        val found = list.find {
            it.first == id
        }

        val result = if(found != null){
            val removedJoke = found.second.toStandardJoke()
            list.remove(found)
            removedJoke
        } else{
            list.add(Pair(id, joke))
            joke.toFavouriteJoke()
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