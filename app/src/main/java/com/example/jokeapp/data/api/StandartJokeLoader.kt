package com.example.jokeapp.data

import com.example.jokeapp.data.api.JokeModelJSON
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.net.UnknownHostException

class StandardJokeLoader(private val gson: Gson): OldJokeLoader {
    override fun getJoke(callback: DownloadCallback) {
        val newThread = object : Thread(){
            override fun run() {
                try{
                    val url: URL = URL(RANDOM_JOKE_URL)
                    val connection: URLConnection = url.openConnection()

                    val inputStreamReader: InputStreamReader = InputStreamReader(connection.getInputStream())

                    val streamToText: String = inputStreamReader.readText()
                    val jokeModelFromJSON = gson.fromJson(streamToText, JokeModelJSON::class.java)
                    callback.returnSuccess(jokeModelFromJSON)

                    inputStreamReader.close()
                } catch (error: Exception){
                    if (error is UnknownHostException){
                        callback.returnError(ErrorType.NO_CONNECTION)
                    } else{
                        callback.returnError(ErrorType.OTHER)
                    }
                }
            }
        }

        newThread.start()
    }

    companion object{
        const val RANDOM_JOKE_URL = "https://official-joke-api.appspot.com/random_joke/"
    }
}