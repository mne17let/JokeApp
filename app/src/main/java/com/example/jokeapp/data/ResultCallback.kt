package com.example.jokeapp.data

interface ResultCallback<S, E> {

    fun onSuccess(data: S)

    fun onFailed(error: E)
}