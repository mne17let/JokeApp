package com.example.jokeapp.data.repository

import com.example.jokeapp.data.*
import com.example.jokeapp.data.api.JokeModelJSON
import com.example.jokeapp.data.dataSources.*
import com.example.jokeapp.data.dataSources.ErrorType

class JokeRepository(private val cloud: CloudDataSource,
                     private val cache: CacheDataSource,
                     private val resourceManager: ErrorResourceManager):
    Repository<Joke, JokeDownloadError> {

    private var callback: ResultCallback? = null

    private var getJokesFromCache = false

    private var count = 0

    private val connectionError = ErrorNoConnection(resourceManager)
    private val serverError = ServerError(resourceManager)
    private val noCachedJokesError = NoFavouriteJokeError(resourceManager)

    private var cachedJokeModelJSON: JokeModelJSON? = null

    override fun getJoke() {

        if(getJokesFromCache){
            cache.getJokeFromCache(object : JokeCachedCallback{
                override fun cachedSuccessfully(jokeModelJSON: JokeModelJSON) {
                    callback?.onDownloadEnd(jokeModelJSON.toFavouriteJoke())
                    cachedJokeModelJSON = jokeModelJSON
                }

                override fun cacheError() {
                    callback?.onDownloadEnd(FailedJoke(noCachedJokesError.getErrorMessage()))
                    cachedJokeModelJSON = null
                }

            })
        } else{
            cloud.getJokeFromCloud(object : CloudCallback{

                override fun onSuccess(jsonJoke: JokeModelJSON) {
                    cachedJokeModelJSON = jsonJoke
                    callback?.onDownloadEnd(jsonJoke.toStandardJoke())
                }

                override fun onError(errorType: ErrorType) {
                    cachedJokeModelJSON = null
                    val fail = if(errorType == ErrorType.NO_CONNECTION) connectionError else serverError
                    callback?.onDownloadEnd(FailedJoke(fail.getErrorMessage()))
                }
            })
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
        val newJoke: Joke? = cachedJokeModelJSON?.checkForCache(cache)
        if (newJoke != null) {
            callback.onDownloadEnd(newJoke)
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