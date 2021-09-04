package com.example.jokeapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokeapp.MainActivity
import com.example.jokeapp.data.*
import com.example.jokeapp.data.repository.JokeRepository
import com.example.jokeapp.data.repository.ResultCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import java.util.function.Function
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

class MyViewModel(private val repository: JokeRepository): ViewModel() {

    private var jokeDataCallback: JokeDataCallback? = null
    private lateinit var uiJoke: UIJoke

    fun setTextCallBack(callback: JokeDataCallback){
        jokeDataCallback = callback
    }

/* suspend fun suspendGetJoke(): UIJoke{
    uiJoke = repository.getJoke()
    return uiJoke
}

val b = object : kotlin.jvm.functions.FunctionN<Unit>{
    override fun invoke(vararg args: Any?) {
        if(jokeDataCallback != null){
            uiJoke.doMap(jokeDataCallback!!)
        }
    }
    override val arity: Int
        get() = 0
}

val smallCoroutineScope = object : CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext

    suspend fun start(){
        val uiJoke = repository.getJoke()
        if(jokeDataCallback != null){
            uiJoke.doMap(jokeDataCallback!!)
        }
    }
}

inner class MyCoroutineScope: CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext

    suspend fun myFun(){
        var a = 3
    }
}

val c: suspend CoroutineScope.() -> Unit = MyCoroutineScope().myFun()

    val a: suspend CoroutineScope.() -> Unit = {
        val uiJoke = repository.getJoke()
        if(jokeDataCallback != null){
            uiJoke.doMap(jokeDataCallback!!)
        }
    }

val block = object : CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = launch{
            val uiJoke = repository.getJoke()
            if(jokeDataCallback != null){
                uiJoke.doMap(jokeDataCallback!!)
            }
        }
} */

    fun getJoke() = viewModelScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT, ) {
        val uiJoke = repository.getJoke()
            if(jokeDataCallback != null){
                uiJoke.doMap(jokeDataCallback!!)
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
        viewModelScope.launch {
            val jokeWithChangedStatus = repository.changeJokeStatus()
            jokeDataCallback?.let {
                jokeWithChangedStatus?.doMap(it)
            }
        }
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