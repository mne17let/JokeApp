package com.example.jokeapp.data.repository

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.UIJoke

/*interface ResultCallback<S, E> {

    fun onSuccess(data: S)

    fun onFailed(error: E)
}*/

interface ResultCallback{
    fun onDownloadEnd(data: UIJoke)
}