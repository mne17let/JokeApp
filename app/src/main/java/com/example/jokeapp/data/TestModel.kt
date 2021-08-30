package com.example.jokeapp.data

import com.example.jokeapp.data.Model
import com.example.jokeapp.data.ResultCallback

class TestModel(private val loader: JokeLoader, private val resourceManager: ErrorResourceManager): Model<Joke, JokeDownloadError> {

    private var callback: ResultCallback<Joke, JokeDownloadError>? = null

    private var count = 0

    private val connectionError = ErrorNoConnection(resourceManager)

    private val serverError = ServerError(resourceManager)

    override fun getJoke() {
        loader.getJoke(object : DownloadCallback{
            override fun returnSuccess(data: JokeModelJSON) {
                callback?.onSuccess(data.toJoke())
            }

            override fun returnError(type: ErrorType) {
                when(type){
                    ErrorType.NO_CONNECTION -> callback?.onFailed(connectionError)
                    ErrorType.OTHER -> callback?.onFailed(serverError)
                }
            }

        })
    }

    override fun start(resultCallback: ResultCallback<Joke, JokeDownloadError>) {
        callback = resultCallback
    }

    override fun clear() {
        callback = null
    }


}