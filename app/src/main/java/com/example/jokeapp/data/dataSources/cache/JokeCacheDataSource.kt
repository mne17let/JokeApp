package com.example.jokeapp.data.dataSources.cache

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.UIJoke
import com.example.jokeapp.data.api.JokeModelJSON
import com.example.jokeapp.data.dataSources.ErrorType
import com.example.jokeapp.data.dataSources.cloud.JokeCloudDataSource

class JokeCacheDataSource: CacheDataSource {

    private val list = ArrayList<Pair<Int, Joke>>()

    private val map = HashMap<Int, JokeModelJSON>()

    override suspend fun addOrRemove(id: Int, joke: Joke): UIJoke {
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

    override suspend fun getJokeFromCache(): JokeCloudDataSource.Result {
        val result: JokeCloudDataSource.Result
        if (list.isEmpty()){
            result = JokeCloudDataSource.Result.Error(ErrorType.NO_CONNECTION)
        } else{
            result = JokeCloudDataSource.Result.JokeData(list.random().second)
        }

        return result
    }
}