package com.example.jokeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokeapp.data.*
import com.example.jokeapp.data.repository.JokeRepository
import com.example.jokeapp.data.repository.ResultCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MyViewModel(private val repository: JokeRepository): ViewModel() {

    private var jokeDataCallback: JokeDataCallback? = null

    fun setTextCallBack(callback: JokeDataCallback){
        jokeDataCallback = callback
    }

    val block = object : CoroutineScope{
        override val coroutineContext: CoroutineContext
            get() = launch{
                val uiJoke = repository.getJoke()
                if(jokeDataCallback != null){
                    uiJoke.doMap(jokeDataCallback!!)
                }
            }
    }

    fun getJoke() = viewModelScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
        launch{
            val uiJoke = repository.getJoke()
            if(jokeDataCallback != null){
                uiJoke.doMap(jokeDataCallback!!)
            }
        }
    }

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