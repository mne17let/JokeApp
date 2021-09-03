package com.example.jokeapp.viewmodel

import com.example.jokeapp.data.*
import com.example.jokeapp.data.repository.JokeRepository
import com.example.jokeapp.data.repository.Repository
import com.example.jokeapp.data.repository.ResultCallback

class ViewModel(private val repository: JokeRepository) {

    private var jokeDataCallback: JokeDataCallback? = null

    fun setTextCallBack(callback: JokeDataCallback){
        jokeDataCallback = callback
    }

    fun getJoke() = repository.getJoke()

    fun start(jokeDataCallback: JokeDataCallback){
        this.jokeDataCallback = jokeDataCallback
        val resultCallback = getResultCallback()
        repository.start(resultCallback)
    }

    fun chooseOnlyFavourite(isChecked: Boolean){
        repository.changeDataSource(isChecked)
    }

    fun changeCurrentJokeStatus(){
        repository.changeJokeStatus(getResultCallback())
    }

    private fun getResultCallback(): ResultCallback {
        val newResultCallback = object : ResultCallback {
            override fun onDownloadEnd(data: UIJoke) {
                jokeDataCallback?.let {
                    data.doMap(it)
                }
            }
        }

        return newResultCallback
    }

    fun clear(){
        jokeDataCallback = null
        repository.clear()
    }
}