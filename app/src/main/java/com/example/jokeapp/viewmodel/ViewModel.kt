package com.example.jokeapp.viewmodel

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.JokeDownloadError
import com.example.jokeapp.data.Model
import com.example.jokeapp.data.ResultCallback

class ViewModel(private val model: Model<Joke, JokeDownloadError>) {

    private var textCallback: TextCallback? = null

    fun setTextCallBack(callback: TextCallback){
        textCallback = callback
    }

    fun getJoke() = model.getJoke()

    fun start(textCallback: TextCallback){
        this.textCallback = textCallback
        val resultCallback = getResultCallback()
        model.start(resultCallback)
    }

    private fun getResultCallback(): ResultCallback<Joke, JokeDownloadError> {
        val newResultCallback = object : ResultCallback<Joke, JokeDownloadError> {
            override fun onSuccess(data: Joke) {
                val jokeText = data.getJokeForUi()
               textCallback?.updateText(jokeText)
            }

            override fun onFailed(error: JokeDownloadError) {
                val errorText = error.getErrorMessage()
                textCallback?.updateText(errorText)
            }
        }

        return newResultCallback
    }

    fun clear(){
        textCallback = null
        model.clear()
    }
}