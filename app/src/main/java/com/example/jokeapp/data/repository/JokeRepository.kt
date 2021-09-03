package com.example.jokeapp.data.repository

import com.example.jokeapp.data.*
import com.example.jokeapp.data.dataSources.*
import com.example.jokeapp.data.dataSources.ErrorType
import com.example.jokeapp.data.dataSources.cache.CacheDataSource
import com.example.jokeapp.data.dataSources.cache.JokeCachedCallback
import com.example.jokeapp.data.dataSources.cloud.CloudDataSource
import com.example.jokeapp.data.dataSources.cloud.JokeCloudDataSource

class JokeRepository(private val cloud: CloudDataSource,
                     private val cache: CacheDataSource,
                     private val resourceManager: ErrorResourceManager):
    Repository {

    private var callback: ResultCallback? = null

    private var getJokesFromCache = false

    private var count = 0

    private val connectionError = ErrorNoConnection(resourceManager)
    private val serverError = ServerError(resourceManager)
    private val noCachedJokesError = NoFavouriteJokeError(resourceManager)

    private var cachedJoke: Joke? = null

    override suspend fun getJoke(): UIJoke {

        if(getJokesFromCache){
            val resultJoke: UIJoke

            val resultFromCache = cache.getJokeFromCache()

            when(resultFromCache){
                is JokeCloudDataSource.Result.JokeData -> {
                    cachedJoke = resultFromCache.data
                    resultJoke = resultFromCache.data.toFavouriteJoke()
                }

                is JokeCloudDataSource.Result.Error -> {
                    cachedJoke = null
                    resultJoke = FailedJoke(noCachedJokesError.getErrorMessage())
                }
            }

            return resultJoke

            /*cache.getJokeFromCache(object : JokeCachedCallback {
                override fun cachedSuccessfully(joke: Joke) {
                    callback?.onDownloadEnd(joke.toFavouriteJoke())
                    cachedJoke = joke
                }

                override fun cacheError() {
                    callback?.onDownloadEnd(FailedJoke(noCachedJokesError.getErrorMessage()))
                    cachedJoke = null
                }

            })*/
        } else{
            val resultJoke: UIJoke

            val resultFromCloud = cloud.getJokeFromCloud()

            when (resultFromCloud){
                is JokeCloudDataSource.Result.JokeData -> {
                    val joke = resultFromCloud.data
                    cachedJoke = joke
                    resultJoke = joke.toStandardJoke()
                }
                is JokeCloudDataSource.Result.Error -> {
                    cachedJoke = null
                    val fail = if (resultFromCloud.errorType == ErrorType.NO_CONNECTION) connectionError else serverError
                    resultJoke = FailedJoke(fail.getErrorMessage())
                }
            }

            return resultJoke

            /*cloud.getJokeFromCloud(object : CloudCallback{

                override fun onSuccess(joke: Joke) {
                    cachedJoke = joke
                    callback?.onDownloadEnd(joke.toStandardJoke())
                }

                override fun onError(errorType: ErrorType) {
                    cachedJoke = null
                    val fail = if(errorType == ErrorType.NO_CONNECTION) connectionError else serverError
                    callback?.onDownloadEnd(FailedJoke(fail.getErrorMessage()))
                }
            })*/
        }

        /*when(count){
            0 -> callback?.onDownloadEnd(StandardJoke("Обычный сетап", "Обычный панч"))
            1 -> callback?.onDownloadEnd(FavouriteJoke("Любимый сетап", "Любимый панч"))
            2 -> callback?.onDownloadEnd(FailedJoke("Обычный сетап"))
        }

        count++

        if(count == 3){
            count = 0
        }*/

        /*loader.getJoke().enqueue(object : retrofit2.Callback<JokeModelJSON>{
            override fun onResponse(call: Call<JokeModelJSON>, response: Response<JokeModelJSON>) {
                if(response.isSuccessful){
                    callback?.onSuccess(response.body()!!.toJoke())
                } else{
                    callback?.onFailed(serverError)
                }
            }

            override fun onFailure(call: Call<JokeModelJSON>, t: Throwable) {
                if(t is UnknownHostException){
                    callback?.onFailed(connectionError)
                } else{
                    callback?.onFailed(serverError)
                }
            }
        })*/

        /*loader.getJoke(object : DownloadCallback{
            override fun returnSuccess(data: JokeModelJSON) {
                callback?.onSuccess(data.toJoke())
            }

            override fun returnError(type: ErrorType) {
                when(type){
                    ErrorType.NO_CONNECTION -> callback?.onFailed(connectionError)
                    ErrorType.OTHER -> callback?.onFailed(serverError)
                }
            }

        })*/
    }

    fun changeJokeStatus(callback: ResultCallback){
        val newJokeForUI: UIJoke? = cachedJoke?.checkForCache(cache)
        if (newJokeForUI != null) {
            callback.onDownloadEnd(newJokeForUI)
        }
    }

    fun changeDataSource(isFavouriteJoke: Boolean){
        getJokesFromCache = isFavouriteJoke
    }

    override fun start(resultCallback: ResultCallback) {
        callback = resultCallback
    }

    override fun clear() {
        callback = null
    }


}