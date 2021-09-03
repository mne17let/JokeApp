package com.example.jokeapp.data.dataSources.cloud

import com.example.jokeapp.data.Joke
import com.example.jokeapp.data.RetrofitJokeLoader
import com.example.jokeapp.data.api.JokeModelJSON
import com.example.jokeapp.data.dataSources.CloudCallback
import com.example.jokeapp.data.dataSources.ErrorType
import retrofit2.Call
import retrofit2.Response
import java.net.UnknownHostException

class JokeCloudDataSource(private val loader: RetrofitJokeLoader): CloudDataSource {
    override suspend fun getJokeFromCloud():  Result{

        try {
            val jsonJoke: JokeModelJSON = loader.getJoke()
            return Result.JokeData(jsonJoke.toJoke())
        } catch (e: Exception){
            if (e is UnknownHostException){
                return Result.Error(ErrorType.NO_CONNECTION)
            } else{
                return Result.Error(ErrorType.SERVICE_UNAVAILABLE)
            }
        }

        /*loader.getJoke().enqueue(object : retrofit2.Callback<JokeModelJSON>{
            override fun onResponse(call: Call<JokeModelJSON>, response: Response<JokeModelJSON>) {
                if(response.isSuccessful){
                    callback.onSuccess(response.body()!!.toJoke())
                } else{
                    callback.onError(ErrorType.SERVICE_UNAVAILABLE)
                }
            }

            override fun onFailure(call: Call<JokeModelJSON>, t: Throwable) {
                if(t is UnknownHostException){
                    callback.onError(ErrorType.NO_CONNECTION)
                } else{
                    callback.onError(ErrorType.SERVICE_UNAVAILABLE)
                }
            }
        })*/
    }

    sealed class Result{
        data class JokeData(val data: Joke): Result()
        data class Error(val errorType: ErrorType): Result()
    }

    /*var count = 0

    override fun getJokeFromCloud(callback: CloudCallback) {
        callback.onSuccess(JokeModelJSON(0, "Тип шутки", "Шутка номер $count", "тестовый панч"))

        count++
    }*/
}