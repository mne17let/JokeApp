package com.example.jokeapp.data

import com.example.jokeapp.data.Model
import com.example.jokeapp.data.ResultCallback

class TestModel(resourceManager: ErrorResourceManager): Model<Joke, JokeDownloadError> {

    private var callback: ResultCallback<Joke, JokeDownloadError>? = null

    private var count = 0

    private val connectionError = ErrorNoConnection(resourceManager)

    private val serverError = ServerError(resourceManager)

    override fun getJoke() {

        val newThread = object : Thread(){
            override fun run() {
                Thread.sleep(3000)

                when(count){
                    0 -> callback?.onSuccess(Joke("Невероятный сетап", "Гениальный панч"))
                    1 -> callback?.onFailed(serverError)
                    2 -> callback?.onFailed(connectionError)
                }
                count++

                if (count == 3){
                    count = 0
                }
            }
        }

        newThread.start()
    }

    override fun start(resultCallback: ResultCallback<Joke, JokeDownloadError>) {
        callback = resultCallback
    }

    override fun clear() {
        callback = null
    }


}